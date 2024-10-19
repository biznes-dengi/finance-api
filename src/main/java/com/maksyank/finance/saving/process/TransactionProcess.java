package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionTransferRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.TransactionAllResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.domain.dto.TransactionUpdateDto;
import com.maksyank.finance.saving.exception.InternalError;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.mapper.context.SavingContext;
import com.maksyank.finance.saving.validation.service.TransactionValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcess {
    private final TransactionDao transactionDao;
    private final SavingDao savingDao;
    private final ProxyProcess proxyProcess;
    private final TransactionValidationService transactionValidationService;
    private final TransactionMapper transactionMapper;

    public TransactionProcess(TransactionDao transactionDao, SavingDao savingDao, ProxyProcess proxyProcess, TransactionValidationService transactionValidationService, TransactionMapper transactionMapper) {
        this.transactionDao = transactionDao;
        this.savingDao = savingDao;
        this.proxyProcess = proxyProcess;
        this.transactionValidationService = transactionValidationService;
        this.transactionMapper = transactionMapper;
    }


    public TransactionAllResponse processGetAll(final int savingId, final int pageNumber, final int boardSavingId)
            throws NotFoundException {
        if (!savingDao.existsSaving(savingId, boardSavingId))
            throw new NotFoundException("Entity 'Saving' not found by attribute 'savingId' = " + savingId);

        final var foundSliceTransaction = transactionDao.fetchAllTransactions(savingId, pageNumber);
        final var mappedTransactionViewResponse =
                transactionMapper.transactionListToTransactionViewResponseList(foundSliceTransaction.getContent());
        return new TransactionAllResponse(mappedTransactionViewResponse, foundSliceTransaction.hasNext());
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionResponse processSave(final TransactionRequest payload, final int savingId, final int boardSavingId)
            throws ParentException {
        final var dtoToSave = transactionMapper.transactionRequestToTransactionDto(payload);
        doValidation(dtoToSave);

        final var linkedSaving = proxyProcess.proxyToUpdateSavingBalance(dtoToSave.amount(), savingId, boardSavingId);
        proxyProcess.proxyToUpdateBoardBalance(boardSavingId, dtoToSave.amount());

        final var newTx = transactionMapper.transactionDtoToTransaction(dtoToSave, new SavingContext(linkedSaving));
        final var response  = transactionDao.createTransaction(newTx);

        return transactionMapper.transactionToTransactionResponse(response);
    }

    // TODO add validation to transactionTimestamp
    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public TransactionResponse processSaveTransactionTransfer(TransactionTransferRequest payload, final int boardSavingId)
            throws ParentException {
        final var dtoToSave = transactionMapper.transactionTransferRequestToTransactionDto(payload);
        doValidation(dtoToSave);

        final var linkedSavings =
                proxyProcess.proxyToUpdateSavingBalancesWhenDoTransferTransaction(
                        boardSavingId,
                        dtoToSave.fromIdGoal(),
                        dtoToSave.toIdGoal(),
                        dtoToSave.amount()
                );

        final var newTxFromSaving = transactionMapper.transactionDtoToTransaction(dtoToSave, new SavingContext(linkedSavings.get(0)));
        final var response = transactionDao.createTransaction(newTxFromSaving);

        final var newTxToSaving = transactionMapper.transactionDtoToTransaction(dtoToSave, new SavingContext(linkedSavings.get(1)));
        transactionDao.createTransaction(newTxToSaving);

        return transactionMapper.transactionToTransactionResponse(response);
    }

    public TransactionResponse processGetById(int depositId, int savingId, int boardSavingId) throws NotFoundException, InternalError {
        final var foundSaving = savingDao.fetchSavingById(savingId, boardSavingId);
        final var foundTransaction = findTransaction(foundSaving, depositId);
        return transactionMapper.transactionToTransactionResponse(foundTransaction);
    }

    public TransactionResponse processUpdate(int depositId, int savingId, TransactionUpdateRequest requestToUpdate, int boardSavingId)
            throws ParentException {
        final var transactionUpdateDto =
                transactionMapper.transactionUpdateRequestToTransactionUpdateDto(requestToUpdate);

        doValidationUpdate(transactionUpdateDto);

        final var foundSaving = this.savingDao.fetchSavingById(savingId, boardSavingId);
        final var transactionToUpdate = this.findTransaction(foundSaving, depositId);
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
    private Transaction findTransaction(Saving source, int depositId) throws NotFoundException {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getId() == depositId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Entity 'Transaction' not found by attribute 'id' = " + depositId));
    }
}
