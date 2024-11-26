package com.finance.app.goal.repository;

import com.finance.app.goal.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    Slice<Transaction> findAllByGoal_Id(int goalId, Pageable pageable);
    void deleteAllByGoal_Id(int goalId);
}
