package com.finance.app.auth.process;

import com.finance.app.account.domain.Account;
import com.finance.app.account.exception.ParentException;
import com.finance.app.account.process.AccountProcess;
import com.finance.app.auth.boundary.request.LoginRequest;
import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.request.ValidationRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.auth.exception.AuthException;
import com.finance.app.auth.utility.Checker;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthProcess {

    private static final String ERROR_MESSAGE = "Exception occurred during [%s]. Please check that data is valid.";

    private final JwtProcess jwtProcess;
    private final PasswordEncoder encoder;
    private final AccountProcess accountProcess;

    public AuthProcess(JwtProcess jwtProcess, PasswordEncoder encoder, AccountProcess accountProcess) {
        this.jwtProcess = jwtProcess;
        this.encoder = encoder;
        this.accountProcess = accountProcess;
    }

    public String loginUser(final LoginRequest request) throws ParentException {
        Account currentAccount;
        if (Checker.checkIfGivenValueIsEmail(request.login()))
            currentAccount = accountProcess.processGetByEmail(request.login());
        else
            currentAccount = accountProcess.processGetByUsername(request.login());

        if (encoder.matches(request.password(), currentAccount.getPassword()))
            return jwtProcess.generateToken(currentAccount.getUsername());
        else
            throw new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("JWT generation"));
    }

    public ValidationResponse validateToken(final ValidationRequest request) {
        final var username = jwtProcess.extractUsername(request.token());
        final var isValid = jwtProcess.validateToken(request.token());
        return new ValidationResponse(username, isValid);
    }

    public String register(final RegisterRequest request) throws ParentException {
        final var account = accountProcess.processCreateNewAccount(request.email(), encoder.encode(request.pass()));
        return Optional.ofNullable(account)
                .map(value -> jwtProcess.generateToken(value.getEmail()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("account registration")));
    }
}
