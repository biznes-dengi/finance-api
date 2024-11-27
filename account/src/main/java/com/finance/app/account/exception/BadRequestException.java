package com.finance.app.account.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ParentException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
