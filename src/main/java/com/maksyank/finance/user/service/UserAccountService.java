package com.maksyank.finance.user.service;

import com.maksyank.finance.user.domain.UserAccount;
import com.maksyank.finance.user.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TO DO refactor userAccount vs user (additionally task in notion 'refactor entity UserAccount')
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount getByEmailAndPassword(String email, String password) {
        final var foundUser = userAccountRepository.findByEmailAndPassword(email, password);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    public UserAccount getById(int id) {
        final var foundUser = userAccountRepository.findById(id);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            // exception not found
        }
        return null;
    }

    public boolean checkIfExists(int id) {
        return this.userAccountRepository.existsById(id);
    }

    public List<Integer> getListIdsOfUsers() {
        return this.userAccountRepository.findAll().stream().map(UserAccount::getId).toList();
    }
}
