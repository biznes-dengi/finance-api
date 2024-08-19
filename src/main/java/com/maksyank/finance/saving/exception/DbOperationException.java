package com.maksyank.finance.saving.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class DbOperationException extends ParentException {

    public DbOperationException(final String message, final Throwable cause) {
        super(INTERNAL_SERVER_ERROR, message, cause);
    }
}
