package com.finance.app.auth.repository;

import com.finance.app.auth.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findTopByEmail(String email);

    boolean existsByEmail(String email);
}
