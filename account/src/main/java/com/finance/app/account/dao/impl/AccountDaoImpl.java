package com.finance.app.account.dao.impl;

import com.finance.app.account.dao.AccountDao;
import com.finance.app.account.domain.Account;
import com.finance.app.account.exception.NotFoundException;
import com.finance.app.account.exception.ParentException;
import com.finance.app.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {
    private final AccountRepository repository;

    @Override
    public List<Integer> fetchListIdsOfAllUsers() {
        return List.of();
    }

    @Override
    public Account fetchAccountById(int id) throws ParentException {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Account by id [" + id + "], was not found"));
    }

    @Override
    public Account fetchAccountByEmail(String email) throws ParentException {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Account by email [" + email + "], was not found"));
    }

    @Override
    public Account fetchAccountByUsername(String username) throws ParentException {
        return repository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Account by username [" + username + "], was not found"));
    }

    @Override
    public Account createAccount(Account source) {
        return repository.save(source);
    }

    @Override
    public boolean existsAccount(int accountId) {
        return false;
    }
}
