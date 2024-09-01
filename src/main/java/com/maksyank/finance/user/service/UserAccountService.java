package com.maksyank.finance.user.service;

import com.maksyank.finance.user.domain.Account;
import com.maksyank.finance.user.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TO DO refactor userAccount vs user (additionally task in notion 'refactor entity UserAccount')
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Account getByEmailAndPassword(String email, String password) {
        final var foundUser = userAccountRepository.findByEmailAndPassword(email, password);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    public Account getById(int id) {
        final var foundUser = userAccountRepository.findById(id);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean checkIfNotExists(int id) {
        return !this.userAccountRepository.existsById(id);
    }

    public List<Integer> getListIdsOfUsers() {
        return this.userAccountRepository.findAll().stream().map(Account::getId).toList();
    }
}
