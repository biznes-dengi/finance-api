package com.finance.app.goal.process;

import com.finance.app.goal.boundary.request.TransactionRequest;
import com.finance.app.goal.boundary.request.TransactionTransferRequest;
import com.finance.app.goal.boundary.request.TransactionUpdateRequest;
import com.finance.app.goal.boundary.response.transaction.TransactionAllResponse;
import com.finance.app.goal.boundary.response.transaction.TransactionNormalResponse;
import com.finance.app.goal.boundary.response.transaction.TransactionResponse;
import com.finance.app.goal.boundary.response.transaction.TransactionTransferResponse;
import com.finance.app.goal.dao.GoalDao;
import com.finance.app.goal.dao.TransactionDao;
import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.Transaction;
import com.finance.app.goal.domain.dto.base.BaseTransactionDto;
import com.finance.app.goal.domain.enums.TransactionType;
import com.finance.app.goal.exception.InternalError;
import com.finance.app.goal.exception.NotFoundException;
import com.finance.app.goal.exception.ParentException;
import com.finance.app.goal.exception.ValidationException;
import com.finance.app.goal.mapper.TransactionMapper;
import com.finance.app.goal.mapper.context.GoalContext;
import com.finance.app.goal.mapper.context.GoalsNameTransferContext;
import com.finance.app.goal.validation.service.ValidationOnlyConstraintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// TODO why here is GoalDao?? must be in proxy?? think about it
@Service
@RequiredArgsConstructor
public class TransactionProcess {
    private final TransactionDao transactionDao;
    private final GoalDao goalDao;
    private final ProxyProcess proxyProcess;
    private final ProxyGoalAndBoard proxyToBoard;
    private final ValidationOnlyConstraintService<BaseTransactionDto> validator;
    private final TransactionMapper transactionMapper;

    public TransactionAllResponse processGetAll(final int goalId, final int pageNumber, final int boardGoalId)
            throws ParentException {
        if (!goalDao.existsGoal(goalId, boardGoalId))
            throw new NotFoundException("Entity 'Goal' not found by attribute 'goalId' = " + goalId);

        final var foundSliceTransaction = transactionDao.fetchAllTransactions(goalId, pageNumber);
        final var mappedTransactionViewResponse =
                customMapperList(foundSliceTransaction.getContent(), boardGoalId);
        return new TransactionAllResponse(mappedTransactionViewResponse, foundSliceTransaction.hasNext());
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionNormalResponse processSave(final TransactionRequest payload, final int goalId, final int boardGoalId)
            throws ParentException {
        final var transactionDto = transactionMapper.transactionRequestToTransactionDto(payload);

        final var resultOfValidation = validator.validate(transactionDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var linkedGoal = proxyProcess.proxyToUpdateGoalBalance(transactionDto.getType(), transactionDto.getAmount(), goalId, boardGoalId);
        proxyToBoard.proxyToUpdateBoardBalance(transactionDto.getType(), transactionDto.getAmount(), boardGoalId);

        final var newTx = transactionMapper.transactionDtoToTransaction(transactionDto, new GoalContext(linkedGoal));
        final var response  = transactionDao.createTransaction(newTx);

        return transactionMapper.transactionToTransactionResponse(response);
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionTransferResponse processSaveTransactionTransfer(TransactionTransferRequest payload, final int boardGoalId)
            throws ParentException {
        final var transactionTransferDto = transactionMapper.transactionTransferRequestToTransactionTransferDto(payload);

        final var resultOfValidation = validator.validate(transactionTransferDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var linkedGoals =
                proxyProcess.proxyToUpdateGoalBalancesByTransferTransaction(transactionTransferDto, boardGoalId);
        final var linkedNamesOfGoals = new GoalsNameTransferContext(
                linkedGoals.get(0).getTitle(),
                linkedGoals.get(1).getTitle()
        );

        final var newTxFromGoal =
                transactionMapper.transactionTransferDtoToTransaction(transactionTransferDto, new GoalContext(linkedGoals.get(0)));
        final var response = transactionDao.createTransaction(newTxFromGoal);

        final var newTxToGoal =
                transactionMapper.transactionTransferDtoToTransaction(transactionTransferDto, new GoalContext(linkedGoals.get(1)));
        transactionDao.createTransaction(newTxToGoal);

        return transactionMapper.transactionToTransactionTransferResponse(response, linkedNamesOfGoals);
    }

    public TransactionResponse processGetById(int depositId, int goalId, int boardGoalId) throws NotFoundException, InternalError {
        final var foundGoal = goalDao.fetchGoalById(goalId, boardGoalId);
        final var foundTransaction = findTransaction(foundGoal, depositId);
        return customMapper(foundTransaction, boardGoalId);
    }

    public TransactionResponse processUpdate(int depositId, int goalId, TransactionUpdateRequest requestToUpdate, int boardGoalId)
            throws ParentException {
        final var transactionUpdateDto =
                transactionMapper.transactionUpdateRequestToTransactionUpdateDto(requestToUpdate);

        final var resultOfValidation = validator.validate(transactionUpdateDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var foundGoal = this.goalDao.fetchGoalById(goalId, boardGoalId);
        final var transactionToUpdate = this.findTransaction(foundGoal, depositId);
        transactionToUpdate.setDescription(transactionUpdateDto.getDescription());
        final var response = this.transactionDao.createTransaction(transactionToUpdate);
        return customMapper(response, boardGoalId);
    }

    // TODO critical point. For big data troubles with time of response
    private Transaction findTransaction(Goal source, int depositId) throws NotFoundException {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getId() == depositId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Entity 'Transaction' not found by attribute 'id' = " + depositId));
    }

    private List<TransactionResponse> customMapperList(List<Transaction> transactions, final int boardGoalId) throws NotFoundException, InternalError {
        final var response = new ArrayList<TransactionResponse>();

        for (var transaction : transactions) {
            if (transaction.getType() == TransactionType.TRANSFER) {
                final var linkedNameGoals = List.of(
                        goalDao.fetchGoalById(transaction.getFromIdGoal(), boardGoalId).getTitle(),
                        goalDao.fetchGoalById(transaction.getToIdGoal(), boardGoalId).getTitle()
                );
                final var transactionTransferResponse = transactionMapper
                        .transactionToTransactionTransferResponse(transaction,
                                new GoalsNameTransferContext(
                                        linkedNameGoals.get(0),
                                        linkedNameGoals.get(1)
                                )
                        );
                response.add(transactionTransferResponse);
            } else {
                final var transactionNormalResponse =
                        transactionMapper.transactionToTransactionResponse(transaction);
                response.add(transactionNormalResponse);
            }
        }
        return response;
    }

    private TransactionResponse customMapper(Transaction transaction, final int boardGoalId) throws NotFoundException, InternalError {
        if (transaction.getType() == TransactionType.TRANSFER) {
            final var linkedNameGoals = List.of(
                    goalDao.fetchGoalById(transaction.getFromIdGoal(), boardGoalId).getTitle(),
                    goalDao.fetchGoalById(transaction.getToIdGoal(), boardGoalId).getTitle()
            );
            final var transactionTransferResponse = transactionMapper
                    .transactionToTransactionTransferResponse(transaction,
                            new GoalsNameTransferContext(
                                    linkedNameGoals.get(0),
                                    linkedNameGoals.get(1)
                            )
                    );
            return transactionTransferResponse;
        } else {
            final var transactionNormalResponse =
                    transactionMapper.transactionToTransactionResponse(transaction);
            return transactionNormalResponse;
        }
    }
}
