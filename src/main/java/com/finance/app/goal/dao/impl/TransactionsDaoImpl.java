package com.finance.app.goal.dao.impl;

import com.finance.app.goal.dao.TransactionDao;
import com.finance.app.goal.domain.Transaction;
import com.finance.app.exception.NotFoundException;
import com.finance.app.goal.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public Slice<Transaction> fetchAllTransactions(int goalId, int pageNumber, Integer pageSize) throws NotFoundException {
        final int currentBatchSize = pageSize == null ? transactionBatchSize : pageSize;
        final var response =
                this.transactionRepository.findAllByGoal_Id(
                        goalId,
                        PageRequest.of(pageNumber, currentBatchSize, Sort.by("transactionTimestamp").descending())
                );

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Transaction' records were found relative to 'goalId' = " +
                    goalId + " and by 'pageNumber' = " + pageNumber);
        }
        return response;
    }

    @Override
    public void removeAllTransactionsByGoalId(int goalId) {
        this.transactionRepository.deleteAllByGoal_Id(goalId);
    }
}
