package com.maksyank.finance.saving.service.validation;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class TransactionDepositValidator extends ValidationService {

    public TransactionDepositValidator(final Validator validator) {
        super(validator);
    }

    public ValidationResult validate(DepositAmountRequest toValidate) {
        return this.validateConstraint(toValidate);
    }
}
