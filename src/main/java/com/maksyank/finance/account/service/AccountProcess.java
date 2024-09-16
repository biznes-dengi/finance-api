package com.maksyank.finance.account.service;

import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountProcess {

    private final AccountRepository accountRepository;

    @Autowired
    AccountProcess(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getByEmailAndPassword(String email, String password) {
        final var foundUser = accountRepository.findByEmailAndPassword(email, password);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    public Account getById(int id) {
        final var foundUser = accountRepository.findById(id);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean checkIfNotExists(int id) {
        return !this.accountRepository.existsById(id);
    }

    public List<Integer> getListIdsOfUsers() {
        return this.accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
