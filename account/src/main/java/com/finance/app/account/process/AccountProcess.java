package com.finance.app.account.process;

import com.finance.app.account.boundary.request.AccountRequest;
import com.finance.app.account.boundary.response.AccountResponse;
import com.finance.app.account.dao.AccountDao;
import com.finance.app.account.domain.Account;
import com.finance.app.account.exception.ParentException;
import com.finance.app.account.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountProcess {
    private final AccountMapper mapper;
    private final AccountDao dao;

    @Transactional(readOnly = true)
    public AccountResponse processGetByUsername(final String username) throws ParentException {
        final var response =  dao.fetchAccountByUsername(username);
        return mapper.accountToAccountResponse(response);
    }

    @Transactional(readOnly = true)
    public AccountResponse processGetByEmail(final String email) throws ParentException {
        final var response = dao.fetchAccountByEmail(email);
        return mapper.accountToAccountResponse(response);
    }

    @Transactional(readOnly = true)
    public AccountResponse processGetById(final int id) throws ParentException {
        final var response = dao.fetchAccountById(id);
        return mapper.accountToAccountResponse(response);
    }

    @Transactional
    public AccountResponse processCreateNewAccount(final AccountRequest request) {
        final var accountDto = mapper.accountRequestToAccountDto(request);
        final var accountToSave = mapper.accountDtoToAccount(accountDto);
        final var response = dao.createAccount(accountToSave);
        return mapper.accountToAccountResponse(response);
    }

    @Transactional(readOnly = true)
    public boolean checkIfNotExists(final int id) {
        return Boolean.FALSE.equals(accountRepository.existsById(id));
    }

    @Transactional(readOnly = true)
    public List<Integer> getListIdsOfUsers() {
        return accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
