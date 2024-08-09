package com.maksyank.finance.saving.service.validation;

import jakarta.validation.Validator;

public abstract class ValidationService {
    protected final Validator validator;
    ValidationService(Validator validator) {
        this.validator = validator;
    }
    protected <T> ValidationResult validateConstraint(T toValidate) {
        final var result = this.validator.validate(toValidate);
        if (!result.isEmpty())
            return ValidationResult.invalid(result.iterator().next().getMessage());

        return ValidationResult.valid();
    }
}
