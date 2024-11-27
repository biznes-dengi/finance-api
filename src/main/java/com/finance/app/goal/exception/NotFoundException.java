package com.finance.app.goal.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ParentException {
    public NotFoundException(final String message) {
        super(NOT_FOUND, message);
    }
}
