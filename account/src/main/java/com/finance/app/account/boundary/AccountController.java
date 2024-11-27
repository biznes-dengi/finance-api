package com.finance.app.account.boundary;

import com.finance.app.account.boundary.request.AccountRequest;
import com.finance.app.account.boundary.response.AccountResponse;
import com.finance.app.account.exception.BadRequestException;
import com.finance.app.account.exception.ParentException;
import com.finance.app.account.process.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AccountController implements AccountBoundaryMetadata {

    private final AccountProcess accountProcess;

    @GetMapping("/{userId}")
    AccountResponse getById(@PathVariable int userId) throws ParentException {
        return accountProcess.processGetById(userId);
    }

    @GetMapping("/{userId}/exist")
    boolean checkIfNotExists(@PathVariable int userId) throws ParentException {
        return accountProcess.processGetById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountResponse create(@RequestBody AccountRequest request) {
        return accountProcess.processCreateNewAccount(request);
    }

    @GetMapping("/login")
    AccountResponse getByLogin(@RequestParam String email, @RequestParam String username) throws ParentException {
        if (email == null && username == null || email != null && username != null) {
            throw new BadRequestException(
                    "Email and username can not both be NULL OR can not be both not NULL. " +
                            "Provide please as minimum one parameter."
            );
        }

        if (email != null)
            return accountProcess.processGetByEmail(email);
        else
            return accountProcess.processGetByUsername(username);
    }
}
