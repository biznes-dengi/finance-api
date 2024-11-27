package com.finance.app.auth.service;

import com.finance.app.auth.boundary.request.LoginRequest;
import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.request.ValidationRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String ERROR_MESSAGE = "Exception occurred during [%s]. Please check that data is valid.";

    private final JwtService jwtService;
    private final AccountProcess accountProcess;

    public String loginUser(final LoginRequest request) {
        final var account = accountProcess.getByEmailAndPassword(request.email(), request.password());
        return Optional.ofNullable(account)
                // change to nickname or something like that
                .map(value -> jwtService.generateToken(value.getEmail()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("JWT generation")));
    }

    public ValidationResponse validateToken(final ValidationRequest request) {
        final var email = jwtService.extractEmail(request.token());
        final var isValid = jwtService.validateToken(request.token());
        return new ValidationResponse(email, isValid);
    }

    public String register(final RegisterRequest request) {
        final var account = accountProcess.createNewAccount(request);
        return Optional.ofNullable(account)
                .map(value -> jwtService.generateToken(value.getEmail()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("account registration")));
    }
}
