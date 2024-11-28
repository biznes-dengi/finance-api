package com.finance.app.auth.process;

import com.finance.app.account.exception.ParentException;
import com.finance.app.account.process.AccountProcess;
import com.finance.app.auth.boundary.request.LoginRequest;
import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.request.TelegramRequest;
import com.finance.app.auth.boundary.request.ValidationRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.auth.exception.AuthException;
import com.finance.app.auth.utility.Checker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthProcess {

    private static final String ERROR_MESSAGE = "Exception occurred during [%s]. Please check that data is valid.";

    private final JwtProcess jwtProcess;
    private final PasswordEncoder encoder;
    private final AccountProcess accountProcess;
    private final TelegramProcess telegramProcess;
    private final UsernameProcess usernameProcess;

    public String login(final LoginRequest request) throws ParentException {
        final var accountResponse = Checker.checkIfGivenValueIsEmail(request.login())
                ? accountProcess.processGetByEmail(request.login())
                : accountProcess.processGetByUsername(request.login());
        if (encoder.matches(request.password(), accountResponse.getPassword())) {
            return jwtProcess.generateToken(accountResponse.username());
        }
        throw new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("JWT generation"));
    }

    public String loginViaTelegram(final TelegramRequest request) {
        final var response = telegramProcess.validate(request);
        if (response.isValid()) {
            final var account = accountProcess.getByTelegramId(response.getTelegramId());
            if (Objects.nonNull(account)) {
                return jwtProcess.generateToken(account.username());
            }
            final var username = Objects.nonNull(response.getUsername())
                    ? response.getUsername()
                    : usernameProcess.generateByFirstName(response.getFirstName());
            return register(new RegisterRequest());
        }
        throw new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("Telegram login process"));
    }

    public ValidationResponse validateToken(final ValidationRequest request) {
        final var email = jwtProcess.extractEmail(request.token());
        final var isValid = jwtProcess.validateToken(request.token());
        return ValidationResponse.aResponse()
                .email(email)
                .valid(isValid)
                .build();
    }

    public String register(final RegisterRequest request) throws ParentException {
        final var account = accountProcess.processCreateNewAccount(request.email(), encoder.encode(request.pass()));
        return Optional.ofNullable(account)
                .map(value -> jwtProcess.generateToken(value.username()))
                .orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE.formatted("account registration")));
    }
}
