package com.finance.app.validation.service;

import com.finance.app.boundary.request.DepositAmountRequest;
import com.finance.app.validation.ValidationResult;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class TransactionDepositValidationService extends ValidationService {

    public TransactionDepositValidationService(final Validator validator) {
        super(validator);
    }

    public ValidationResult validate(DepositAmountRequest toValidate) {
        return this.validateConstraint(toValidate);
    }
}
