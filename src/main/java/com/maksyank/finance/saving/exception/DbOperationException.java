package com.maksyank.finance.saving.exception;

public class DbOperationException extends Exception {
    public DbOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
