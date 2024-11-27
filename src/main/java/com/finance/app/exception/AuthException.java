package com.finance.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final HttpStatus status;

    public AuthException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }
}
