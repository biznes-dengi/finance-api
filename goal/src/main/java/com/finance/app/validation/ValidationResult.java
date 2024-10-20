package com.finance.app.validation;

public record ValidationResult(boolean isValid, String errorMsg) {
    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }
    public static ValidationResult invalid(String errorMsg) {
        return new ValidationResult(false, errorMsg);
    }
    public boolean notValid() {
        return !isValid;
    }
}