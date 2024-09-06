package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.StateOfSavingResponseMapper;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.validation.service.TransactionValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionProcess {
    private final TransactionDao transactionDao;
    private final SavingDao savingDao;
    private final SavingProcess savingProcess;
    private final TransactionValidationService transactionValidationService;
    private final StateOfSavingResponseMapper stateOfSavingResponseMapper;
    private final TransactionMapper transactionMapper;

    public List<TransactionViewResponse> processGetByPage(int savingId, int pageNumber, int boardSavingId) throws NotFoundException {
        boolean ifExists = savingDao.existsSaving(savingId, boardSavingId);
        if (!ifExists) {
            throw new NotFoundException("Entity 'Saving' not found by attribute 'savingId' = " + savingId);
        }

        final var foundTransactions =
                this.transactionDao.fetchTransactionsBySavingIdPageable(savingId, pageNumber);
        return transactionMapper.transactionListToTransactionViewResponseList(foundTransactions);
    }

    // TODO add validation to dealDate
    public StateOfSavingResponse processSave(TransactionRequest requestToSave, int savingId, int boardSavingId)
            throws NotFoundException, ValidationException {
        final var transactionDtoToSave = transactionMapper.transactionRequestToTransactionDto(requestToSave);

        final var resultOfValidation = transactionValidationService.validate(transactionDtoToSave);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var linkedSaving = savingProcess.updateBalance(transactionDtoToSave.amount(), savingId, boardSavingId);

        final var newTransaction = createNewTransaction(transactionDtoToSave, linkedSaving);
        this.transactionDao.createTransaction(newTransaction);

        return stateOfSavingResponseMapper.savingToStateOfSavingResponse(linkedSaving);
    }

    public TransactionResponse processGetById(int depositId, int savingId, int boardSavingId) throws NotFoundException {
        final var foundSaving = savingDao.fetchSavingById(savingId, boardSavingId);
        final var foundTransaction = findTransaction(foundSaving, depositId);
        return transactionMapper.transactionToTransactionResponse(foundTransaction);
    }

    public TransactionResponse processUpdate(int depositId, int savingId, TransactionUpdateRequest requestToUpdate, int boardSavingId)
            throws NotFoundException, ValidationException {
        final var transactionUpdateDto =
                transactionMapper.transactionUpdateRequestToTransactionUpdateDto(requestToUpdate);

        final var resultOfValidation = this.transactionValidationService.validate(transactionUpdateDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var foundSaving = this.savingDao.fetchSavingById(savingId, boardSavingId);
        final var transactionToUpdate = this.findTransaction(foundSaving, depositId);
        transactionToUpdate.setDescription(transactionUpdateDto.description());
        final var response = this.transactionDao.createTransaction(transactionToUpdate);
        return transactionMapper.transactionToTransactionResponse(response);
    }

    private Transaction createNewTransaction(TransactionDto transactionDtoToSave, Saving linkedSaving) {
        return new Transaction(
                transactionDtoToSave.type(),
                transactionDtoToSave.description(),
                transactionDtoToSave.dealDate(),
                transactionDtoToSave.amount(),
                linkedSaving
        );
    }

    // TODO critical point. For big data troubles with time of response
    private Transaction findTransaction(Saving source, int depositId) throws NotFoundException {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getId() == depositId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Entity 'Transaction' not found by attribute 'id' = " + depositId));
    }
}
