package com.finance.app.account.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ParentException {
    public NotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
