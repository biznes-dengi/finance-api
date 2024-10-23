package com.finance.app.service;

import com.finance.app.boundary.request.LoginRequest;
import com.finance.app.boundary.request.RegisterRequest;
import com.finance.app.boundary.request.ValidationRequest;
import com.finance.app.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AccountProcess accountProcess;

    public String loginUser(final LoginRequest request) {
        final var account = accountProcess.getByEmailAndPassword(request.email(), request.password());
        if (Objects.nonNull(account)) {
            return jwtService.generateToken(account.getEmail());
        }
        throw new AuthException(HttpStatus.UNAUTHORIZED, "Exception occurred during JWT generation. Please check that data is valid.");
    }

    public Boolean validateToken(final ValidationRequest request) {
        return jwtService.validateToken(request.token(), request.username());
    }

    public String register(final RegisterRequest request) {
        final var account = accountProcess.createNewAccount(request);
        if (Objects.nonNull(account)) {
            return jwtService.generateToken(account.getEmail());
        }
        throw new AuthException(HttpStatus.BAD_REQUEST, "Exception occurred during account registration. Please check that data is valid.");
    }
}
