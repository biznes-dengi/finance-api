package com.maksyank.finance.saving.dao.impl;

import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionsDaoImpl implements TransactionDao {
    private final TransactionRepository transactionRepository;
    @Value("${transaction.batch-size}")
    private int transactionBatchSize;

    @Override
    public Transaction createTransaction(Transaction transactionToSave) {
        return transactionRepository.save(transactionToSave);
    }

    @Override
    public Slice<Transaction> fetchAllTransactions(int savingId, int pageNumber) throws NotFoundException {
        final var response =
                this.transactionRepository.findAllBySaving_Id(savingId, PageRequest.of(pageNumber, transactionBatchSize));

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Transaction' records were found relative to 'savingId' = " +
                    savingId + " and by 'pageNumber' = " + pageNumber);
        }
        return response;
    }

    @Override
    public void removeAllTransactionsBySavingId(int savingId) {
        this.transactionRepository.deleteAllBySaving_Id(savingId);
    }
}
