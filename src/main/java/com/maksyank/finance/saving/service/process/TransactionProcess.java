package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.dto.TransactionDto;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.StateOfSavingResponseMapper;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import com.maksyank.finance.saving.service.persistence.TransactionPersistence;
import com.maksyank.finance.saving.service.validation.service.TransactionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionProcess {
    private final TransactionPersistence transactionPersistence;
    private final SavingPersistence savingPersistence;
    private final SavingProcess savingProcess;
    private final TransactionValidationService transactionValidationService;
    private final StateOfSavingResponseMapper stateOfSavingResponseMapper;
    private final TransactionMapper transactionMapper;

    @Autowired
    TransactionProcess(
            TransactionPersistence transactionPersistence,
            SavingPersistence savingPersistence,
            SavingProcess savingProcess,
            TransactionValidationService transactionValidationService,
            StateOfSavingResponseMapper stateOfSavingResponseMapper,
            TransactionMapper transactionMapper
    ) {
        this.transactionPersistence = transactionPersistence;
        this.savingPersistence = savingPersistence;
        this.savingProcess = savingProcess;
        this.transactionValidationService = transactionValidationService;
        this.stateOfSavingResponseMapper = stateOfSavingResponseMapper;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionViewResponse> processGetByPage(int savingId, int pageNumber, int userId) throws NotFoundException {
        boolean ifExists = this.savingPersistence.ifExistsByIdAndUserId(savingId, userId);
        if (!ifExists) {
            throw new NotFoundException("Entity 'Saving' not found by attribute 'savingId' = " + savingId);
        }

        final var foundTransactions =
                this.transactionPersistence.findAllBySavingIdPageable(savingId, pageNumber);
        return transactionMapper.transactionListToTransactionViewResponseList(foundTransactions);
    }

    // TODO add validation to dealDate
    public StateOfSavingResponse processSave(TransactionRequest requestToSave, int savingId, int userId)
            throws NotFoundException, DbOperationException, ValidationException {
        final var transactionDtoToSave = transactionMapper.transactionRequestToTransactionDto(requestToSave);

        final var resultOfValidation = transactionValidationService.validate(transactionDtoToSave);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var linkedSaving = savingProcess.updateBalance(transactionDtoToSave.amount(), savingId, userId);

        final var newTransaction = createNewTransaction(transactionDtoToSave, linkedSaving);
        this.transactionPersistence.save(newTransaction);

        return stateOfSavingResponseMapper.savingToStateOfSavingResponse(linkedSaving);
    }

    public TransactionResponse processGetById(int depositId, int savingId, int userId) throws NotFoundException {
        final var saving = this.savingPersistence.findByIdAndUserId(savingId, userId);
        final var foundTransaction = this.findTransaction(saving, depositId);
        return transactionMapper.transactionToTransactionResponse(foundTransaction);
    }

    public boolean processUpdate(int depositId, int financeGoalId, TransactionUpdateRequest requestToUpdate, int userId)
            throws NotFoundException, DbOperationException, ValidationException {
        final var transactionUpdateDto =
                transactionMapper.transactionUpdateRequestToTransactionUpdateDto(requestToUpdate);

        final var resultOfValidation = this.transactionValidationService.validate(transactionUpdateDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var saving = this.savingPersistence.findByIdAndUserId(financeGoalId, userId);
        final var transactionToUpdate = this.findTransaction(saving, depositId);
        transactionToUpdate.setDescription(transactionUpdateDto.description());
        return this.transactionPersistence.save(transactionToUpdate);
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
