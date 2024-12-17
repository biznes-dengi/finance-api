package com.finance.app.auth.service;

import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.response.AccountResponse;
import com.finance.app.auth.domain.Account;
import com.finance.app.auth.mapper.AccountMapper;
import com.finance.app.auth.repository.AccountRepository;
import com.finance.app.exception.NotFoundException;
import com.finance.app.goal.dao.BoardGoalDao;
import com.finance.app.goal.domain.BoardGoal;
import com.finance.app.goal.domain.enums.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountProcess {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    private final BoardGoalDao boardGoalDao;

    public Account getByEmailAndPassword(final String email, final String password) {
        return accountRepository.findTopByEmail(email)
                .filter(account -> encoder.matches(password, account.getPassword()))
                .orElse(null);
    }

    public AccountResponse getByEmail(final String email) throws NotFoundException {
        final var optionalAccount =  accountRepository.findTopByEmail(email);

        if (optionalAccount.isPresent()) {
            final var account = optionalAccount.get();
            return new AccountResponse(account.getId(), account.getEmail(), account.getNickname(), account.getCreatedOn(), account.getLastLogin());
        } else {
            throw new NotFoundException("Account not found by email : " + email);
        }
    }

    public Account getById(final int id) {
        return accountRepository.findById(id)
                .orElse(null); // should return exception not found
    }

    @Transactional
    public Account createNewAccount(final RegisterRequest request) {
        final var accountToSave = accountMapper.accountRequestToAccount(request, encoder);
        final var response = accountRepository.save(accountToSave);
        boardGoalDao.createBoardGoal(initNewBoardGoalToSave(response));
        return response;
    }

    private BoardGoal initNewBoardGoalToSave(Account account) {
        final var initBalance = BigDecimal.ZERO;
        final CurrencyCode code = CurrencyCode.USD;
        return new BoardGoal(account, initBalance, code);
    }

    public boolean checkIfNotExists(final int id) {
        return Boolean.FALSE.equals(accountRepository.existsById(id));
    }

    @Transactional(readOnly = true)
    public boolean checkIfExistsByEmail(final String email) {
        return accountRepository.existsByEmail(email);
    }

    public List<Integer> getListIdsOfUsers() {
        return accountRepository.findAll().stream().map(Account::getId).toList();
    }
}
