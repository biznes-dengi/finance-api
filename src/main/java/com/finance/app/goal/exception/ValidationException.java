package com.finance.app.goal.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidationException extends ParentException {

    public ValidationException(String message) {
        super(BAD_REQUEST, message);
    }
}
