package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.dto.TransactionDto;
import com.maksyank.finance.saving.dto.TransactionUpdateDto;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.mapper.SavingMapper;
import com.maksyank.finance.saving.service.persistence.TransactionPersistence;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import com.maksyank.finance.saving.service.validation.TransactionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionProcess {
    private TransactionPersistence transactionPersistence;
    private SavingPersistence savingPersistence;
    private SavingProcess savingProcess;
    private TransactionValidationService transactionValidationService;

    @Autowired
    TransactionProcess(
            TransactionPersistence transactionPersistence,
            SavingPersistence savingPersistence,
            SavingProcess savingProcess,
            TransactionValidationService transactionValidationService
    ) {
        this.transactionPersistence = transactionPersistence;
        this.savingPersistence = savingPersistence;
        this.savingProcess = savingProcess;
        this.transactionValidationService = transactionValidationService;
    }

    public List<TransactionViewResponse> processGetByPage(int savingId, int pageNumber, int userId) throws NotFoundException {
        boolean ifExists = this.savingPersistence.ifExistsByIdAndUserId(savingId, userId);
        if (!ifExists) {
            throw new NotFoundException("Entity 'Finance Goal' not found by attribute 'savingId' = " + savingId);
        }

        final var foundTransactions = this.transactionPersistence.findAllBySavingIdPageable(savingId, pageNumber);
        return TransactionMapper.entityToViewResponse(foundTransactions);
    }

    // TODO add validation to fundingDate
    public StateOfSavingResponse processSave(TransactionDto depositToSaveDto, int savingId, int userId) throws NotFoundException, DbOperationException, ValidationException {
        final var resultOfValidation = this.transactionValidationService.validate(depositToSaveDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var saving = this.savingProcess.updateBalance(depositToSaveDto.amount(), savingId, userId);
        final var depositToSave = TransactionMapper.mapToNewEntity(depositToSaveDto, saving);
        this.transactionPersistence.save(depositToSave);

        return SavingMapper.mapToStateResponse(saving);
    }

    public TransactionResponse processGetById(int depositId, int savingId, int userId) throws NotFoundException {
        final var saving = this.savingPersistence.findByIdAndUserId(savingId, userId);
        final var foundDeposit = this.findTransaction(saving, depositId);
        return TransactionMapper.entityToResponse(foundDeposit);
    }

    public boolean processUpdate(int depositId, int financeGoalId, TransactionUpdateDto transactionUpdateDto, int userId)
            throws NotFoundException, DbOperationException, ValidationException {
        final var resultOfValidation = this.transactionValidationService.validate(transactionUpdateDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var saving = this.savingPersistence.findByIdAndUserId(financeGoalId, userId);
        final var depositToUpdate = this.findTransaction(saving, depositId);
        depositToUpdate.setDescription(transactionUpdateDto.description());
        return this.transactionPersistence.save(depositToUpdate);
    }

    // TODO critical point. For big data troubles with time of response
    private Transaction findTransaction(Saving source, int depositId) throws NotFoundException {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getId() == depositId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Entity 'Deposit' not found by attribute 'id' = " + depositId));
    }
}
