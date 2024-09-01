package com.maksyank.finance.user.repository;

import com.maksyank.finance.user.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmailAndPassword(String email, String password);
}
