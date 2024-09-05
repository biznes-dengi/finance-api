package com.maksyank.finance.saving.dao.impl;

import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsDaoImpl implements TransactionDao {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transactionToSave) {
        return transactionRepository.save(transactionToSave);
    }

    // TODO (refactor) A client will have the same exceptions two cases:
    // TODO If there were 10 items and they just ran out or were not there originally
    @Override
    public List<Transaction> fetchTransactionsBySavingIdPageable(int savingId, int pageNumber) throws NotFoundException {
        final var response =
                this.transactionRepository.findAllBySaving_Id(savingId, PageRequest.of(pageNumber, 5));

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("Entities 'Transaction' not found by attribute " +
                    "'savingId' = " + savingId + ", and by 'pageNumber' = " + pageNumber);
        }
        return response.getContent();
    }

    @Override
    public void removeAllTransactionsBySavingId(int savingId) {
        this.transactionRepository.deleteAllBySaving_Id(savingId);
    }
}
