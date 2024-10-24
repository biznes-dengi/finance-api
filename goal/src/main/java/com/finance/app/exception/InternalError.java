package com.finance.app.exception;

import org.springframework.http.HttpStatus;

public class InternalError extends ParentException {
    public InternalError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
