package com.finance.app.goal.validation.service;

import com.finance.app.validation.ValidationResult;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidationOnlyConstraintService<T> extends ValidationService {

    ValidationOnlyConstraintService(Validator validator) {
        super(validator);
    }

    public ValidationResult
    validate(T toValidate) {
        return this.validateConstraint(toValidate);
    }
}
