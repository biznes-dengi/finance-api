package com.finance.app.process;

import com.finance.app.boundary.request.TransactionRequest;
import com.finance.app.boundary.request.TransactionTransferRequest;
import com.finance.app.boundary.request.TransactionUpdateRequest;
import com.finance.app.boundary.response.TransactionAllResponse;
import com.finance.app.boundary.response.TransactionResponse;
import com.finance.app.dao.GoalDao;
import com.finance.app.dao.TransactionDao;
import com.finance.app.domain.Goal;
import com.finance.app.domain.Transaction;
import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.domain.dto.TransactionUpdateDto;
import com.finance.app.exception.InternalError;
import com.finance.app.exception.NotFoundException;
import com.finance.app.exception.ParentException;
import com.finance.app.exception.ValidationException;
import com.finance.app.mapper.TransactionMapper;
import com.finance.app.mapper.context.GoalContext;
import com.finance.app.validation.service.TransactionValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcess {
    private final TransactionDao transactionDao;
    private final GoalDao goalDao;
    private final ProxyProcess proxyProcess;
    private final TransactionValidationService transactionValidationService;
    private final TransactionMapper transactionMapper;

    public TransactionProcess(TransactionDao transactionDao, GoalDao goalDao, ProxyProcess proxyProcess, TransactionValidationService transactionValidationService, TransactionMapper transactionMapper) {
        this.transactionDao = transactionDao;
        this.goalDao = goalDao;
        this.proxyProcess = proxyProcess;
        this.transactionValidationService = transactionValidationService;
        this.transactionMapper = transactionMapper;
    }


    public TransactionAllResponse processGetAll(final int goalId, final int pageNumber, final int boardGoalId)
            throws NotFoundException {
        if (!goalDao.existsGoal(goalId, boardGoalId))
            throw new NotFoundException("Entity 'Goal' not found by attribute 'goalId' = " + goalId);

        final var foundSliceTransaction = transactionDao.fetchAllTransactions(goalId, pageNumber);
        final var mappedTransactionViewResponse =
                transactionMapper.transactionListToTransactionViewResponseList(foundSliceTransaction.getContent());
        return new TransactionAllResponse(mappedTransactionViewResponse, foundSliceTransaction.hasNext());
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionResponse processSave(final TransactionRequest payload, final int goalId, final int boardGoalId)
            throws ParentException {
        final var dtoToSave = transactionMapper.transactionRequestToTransactionDto(payload);
        doValidation(dtoToSave);

        final var linkedGoal = proxyProcess.proxyToUpdateGoalBalance(dtoToSave.amount(), goalId, boardGoalId);
        proxyProcess.proxyToUpdateBoardBalance(boardGoalId, dtoToSave.amount());

        final var newTx = transactionMapper.transactionDtoToTransaction(dtoToSave, new GoalContext(linkedGoal));
        final var response  = transactionDao.createTransaction(newTx);

        return transactionMapper.transactionToTransactionResponse(response);
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionResponse processSaveTransactionTransfer(TransactionTransferRequest payload, final int boardGoalId)
            throws ParentException {
        final var dtoToSave = transactionMapper.transactionTransferRequestToTransactionDto(payload);
        doValidation(dtoToSave);

        final var linkedGoals =
                proxyProcess.proxyToUpdateGoalBalancesWhenDoTransferTransaction(
                        boardGoalId,
                        dtoToSave.fromIdGoal(),
                        dtoToSave.toIdGoal(),
                        dtoToSave.amount()
                );

        final var newTxFromGoal = transactionMapper.transactionDtoToTransaction(dtoToSave, new GoalContext(linkedGoals.get(0)));
        final var response = transactionDao.createTransaction(newTxFromGoal);

        final var newTxToGoal = transactionMapper.transactionDtoToTransaction(dtoToSave, new GoalContext(linkedGoals.get(1)));
        transactionDao.createTransaction(newTxToGoal);

        return transactionMapper.transactionToTransactionResponse(response);
    }

    public TransactionResponse processGetById(int depositId, int goalId, int boardGoalId) throws NotFoundException, InternalError {
        final var foundGoal = goalDao.fetchGoalById(goalId, boardGoalId);
        final var foundTransaction = findTransaction(foundGoal, depositId);
        return transactionMapper.transactionToTransactionResponse(foundTransaction);
    }

    public TransactionResponse processUpdate(int depositId, int goalId, TransactionUpdateRequest requestToUpdate, int boardGoalId)
            throws ParentException {
        final var transactionUpdateDto =
                transactionMapper.transactionUpdateRequestToTransactionUpdateDto(requestToUpdate);

        doValidationUpdate(transactionUpdateDto);

        final var foundGoal = this.goalDao.fetchGoalById(goalId, boardGoalId);
        final var transactionToUpdate = this.findTransaction(foundGoal, depositId);
        transactionToUpdate.setDescription(transactionUpdateDto.description());
        final var response = this.transactionDao.createTransaction(transactionToUpdate);
        return transactionMapper.transactionToTransactionResponse(response);
    }

    private void doValidation(TransactionDto payload) throws ValidationException {
        final var resultOfValidation = transactionValidationService.validate(payload);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());
    }

    // TODO refactor. Merge doValidationUpdate and doValidation.
    private void doValidationUpdate(TransactionUpdateDto payload) throws ValidationException {
        final var resultOfValidation = transactionValidationService.validate(payload);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());
    }

    // TODO critical point. For big data troubles with time of response
    private Transaction findTransaction(Goal source, int depositId) throws NotFoundException {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getId() == depositId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Entity 'Transaction' not found by attribute 'id' = " + depositId));
    }
}
