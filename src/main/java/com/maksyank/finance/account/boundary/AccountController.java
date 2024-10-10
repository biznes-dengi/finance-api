package com.maksyank.finance.account.boundary;

import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.account.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class AccountController {
    private final AccountProcess accountProcess;

    @PostMapping
    @ResponseStatus(CREATED)
    public Account save(@RequestBody AccountRequest request) {
        return accountProcess.createNewAccount(request);
    }
}
