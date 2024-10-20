package com.finance.app.dao;

import com.finance.app.domain.Transaction;
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
     * Fetch Slice of transactions by given savingId.
     *
     * @param savingId unique identifier of Saving entity which must find
     * @param pageNumber number of page with Transactions
     * @return batch of Transactions
     */
    Slice<Transaction> fetchAllTransactions(int savingId, int pageNumber) throws NotFoundException;

    /**
     * Remove all transaction records by given savingId
     *
     * @param savingId unique identifier of Saving entity which must find
     */
    void removeAllTransactionsBySavingId(int savingId);
}
