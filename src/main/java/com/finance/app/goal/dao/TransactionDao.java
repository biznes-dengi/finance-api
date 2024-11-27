package com.finance.app.goal.dao;

import com.finance.app.goal.domain.Transaction;
import com.finance.app.exception.NotFoundException;
import org.springframework.data.domain.Slice;

public interface TransactionDao {

    /**
     * Create new Transaction record
     *
     * @param transactionToSave source of Transaction for create
     * @return new created transaction
     */
    Transaction createTransaction(Transaction transactionToSave);

    /**
     * Fetch Slice of transactions by given goalId.
     *
     * @param goalId unique identifier of Goal entity which must find
     * @param pageNumber number of page with Transactions
     * @return batch of Transactions
     */
    Slice<Transaction> fetchAllTransactions(int goalId, int pageNumber) throws NotFoundException;

    /**
     * Remove all transaction records by given goalId
     *
     * @param goalId unique identifier of Goal entity which must find
     */
    void removeAllTransactionsByGoalId(int goalId);
}
