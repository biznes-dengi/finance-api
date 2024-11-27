package com.finance.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ParentException extends Exception {

    protected final HttpStatus status;

    public ParentException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

    public ParentException(final HttpStatus status, final String message, final Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }
}
