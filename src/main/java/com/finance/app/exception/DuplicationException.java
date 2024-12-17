package com.finance.app.exception;

import org.springframework.http.HttpStatus;

public class DuplicationException extends ParentException {

    public DuplicationException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
