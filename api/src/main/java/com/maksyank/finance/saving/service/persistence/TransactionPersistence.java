package com.maksyank.finance.saving.service.persistence;

import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionPersistence {
    private TransactionRepository transactionRepository;

    @Autowired
    TransactionPersistence(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // TODO (refactor) A client will have the same exceptions two cases:
    // TODO If there were 10 items and they just ran out or were not there originally
    public List<Transaction> findAllBySavingIdPageable(int savingId, int pageNumber) throws NotFoundException {
        final var response =
                this.transactionRepository.findAllBySaving_Id(savingId, PageRequest.of(pageNumber, 5));

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("Entities 'Transaction' not found by attribute " +
                    "'savingId' = " + savingId + ", and by 'pageNumber' = " + pageNumber);
        }
        return response.getContent();
    }

    // TODO refactor handling type
    public boolean save(Transaction preparedTransaction) throws DbOperationException {
        try {
            this.transactionRepository.save(preparedTransaction);
            return true;
        } catch (Exception ex) {
            throw new DbOperationException(ex.getMessage(), ex);
        }
    }

    // TODO Must be return boolean?
    // TODO refactor handling type
    public void removeAllBySavingId(int financeGoalId) throws DbOperationException {
        try {
            this.transactionRepository.deleteAllBySaving_Id(financeGoalId);
        } catch (Exception ex) {
            throw new DbOperationException(ex.getMessage(), ex);
        }
    }
}
