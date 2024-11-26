package com.finance.app.account.process;

import com.finance.app.account.domain.Account;
import com.finance.app.account.exception.NotFoundException;
import com.finance.app.account.exception.ParentException;
import com.finance.app.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountProcess {
    private final AccountRepository accountRepository;

    public Account processGetByUsername(final String username) throws ParentException {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Account by username [" + username + "], was not found"));
    }

    public Account processGetByEmail(final String email) throws ParentException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Account by email [" + email + "], was not found"));
    }

    public Account processGetById(final int id) throws NotFoundException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Account by id [" + id + "], was not found"));
    }

    @Transactional
    public Account createNewAccount(final String email, final String pass) throws ParentException {
        final var accountToSave = new Account(email, pass);
        return accountRepository.save(accountToSave);
    }

    public boolean checkIfNotExists(final int id) {
        return Boolean.FALSE.equals(accountRepository.existsById(id));
    }

    public List<Integer> getListIdsOfUsers() {
        return accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
