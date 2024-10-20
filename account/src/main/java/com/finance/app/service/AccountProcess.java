package com.finance.app.service;

import com.finance.app.boundary.AccountRequest;
import com.finance.app.domain.Account;
import com.finance.app.mapper.AccountMapper;
import com.finance.app.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountProcess {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

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

    public Account createNewAccount(AccountRequest request) {
        final var accountToSave = accountMapper.accountRequestToAccount(request);
        return accountRepository.save(accountToSave);
    }

    @Transactional(readOnly = true)
    public boolean checkIfNotExists(int id) {
        return !this.accountRepository.existsById(id);
    }

    public List<Integer> getListIdsOfUsers() {
        return this.accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
