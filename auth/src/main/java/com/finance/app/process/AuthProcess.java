package com.finance.app.process;

import com.finance.app.boundary.request.LoginRequest;
import com.finance.app.boundary.request.RegisterRequest;
import com.finance.app.boundary.request.ValidationRequest;
import com.finance.app.boundary.response.ValidationResponse;
import com.finance.app.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthProcess {

    private static final String ERROR_MESSAGE = "Exception occurred during [%s]. Please check that data is valid.";

    private final JwtProcess jwtProcess;
    private final AccountProcess accountProcess;

    public String loginUser(final LoginRequest request) {
        final var account = accountProcess.getByEmailAndPassword(request.email(), request.password());
        return Optional.ofNullable(account)
                // change to nickname or something like that
                .map(value -> jwtProcess.generateToken(value.getEmail()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("JWT generation")));
    }

    public ValidationResponse validateToken(final ValidationRequest request) {
        final var email = jwtProcess.extractEmail(request.token());
        final var isValid = jwtProcess.validateToken(request.token());
        return new ValidationResponse(email, isValid);
    }

    public String register(final RegisterRequest request) {
        final var account = accountProcess.createNewAccount(request);
        return Optional.ofNullable(account)
                .map(value -> jwtProcess.generateToken(value.getEmail()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("account registration")));
    }
}
