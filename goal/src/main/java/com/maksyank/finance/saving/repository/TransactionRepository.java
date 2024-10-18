package com.maksyank.finance.saving.repository;

import com.maksyank.finance.saving.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    Slice<Transaction> findAllBySaving_Id(int savingId, Pageable pageable);
    void deleteAllBySaving_Id(int savingId);
}
