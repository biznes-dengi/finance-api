package com.maksyank.finance.account.repository;

import com.maksyank.finance.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmailAndPassword(String email, String password);
}
