package com.finance.app.service;

import com.finance.app.boundary.request.RegisterRequest;
import com.finance.app.domain.Account;
import com.finance.app.mapper.AccountMapper;
import com.finance.app.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountProcess {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;

    public Account getByEmailAndPassword(final String email, final String password) {
        return accountRepository.findTopByEmail(email)
                .filter(account -> encoder.matches(password, account.getPassword()))
                .orElse(null); // should return exception not found
    }

    public Account getById(final int id) {
        return accountRepository.findById(id)
                .orElse(null); // should return exception not found
    }

    @Transactional
    public Account createNewAccount(final RegisterRequest request) {
        final var accountToSave = accountMapper.accountRequestToAccount(request, encoder);
        return accountRepository.save(accountToSave);
    }

    public boolean checkIfNotExists(final int id) {
        return Boolean.FALSE.equals(accountRepository.existsById(id));
    }

    public List<Integer> getListIdsOfUsers() {
        return accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
