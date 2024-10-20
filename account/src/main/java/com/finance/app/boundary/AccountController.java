package com.finance.app.boundary;

import com.finance.app.domain.Account;
import com.finance.app.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
