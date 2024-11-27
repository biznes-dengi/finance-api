package com.finance.app.auth.boundary;

import com.finance.app.auth.boundary.response.AccountResponse;
import com.finance.app.auth.domain.Account;
import com.finance.app.auth.service.AccountProcess;
import com.finance.app.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AccountProcess accountProcess;

    @GetMapping("/me")
    AccountResponse getLoggedUser(Principal principal) throws NotFoundException {
        final var email = principal.getName();
        return accountProcess.getByEmail(email);
    }
}
