package com.maksyank.finance.saving.service.validation.service;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import com.maksyank.finance.saving.service.validation.ValidationResult;
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
