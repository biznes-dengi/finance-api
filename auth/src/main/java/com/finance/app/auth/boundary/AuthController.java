package com.finance.app.auth.boundary;

import com.finance.app.account.exception.ParentException;
import com.finance.app.auth.boundary.request.LoginRequest;
import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.request.TelegramRequest;
import com.finance.app.auth.boundary.request.ValidationRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.auth.process.AuthProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthBoundaryMetadata {

    private final AuthProcess service;

    @PostMapping("/login")
    public String generate(@RequestBody @Validated final LoginRequest request) throws ParentException {
        return service.login(request);
    }

    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody @Validated final ValidationRequest request) {
        return service.validateToken(request);
    }

    @PostMapping("/telegram")
    public String loginViaTelegram(@RequestBody final TelegramRequest request) {
        return service.loginViaTelegram(request);
    }

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public String save(@RequestBody @Validated final RegisterRequest request) throws ParentException {
        return service.register(request);
    }
}
