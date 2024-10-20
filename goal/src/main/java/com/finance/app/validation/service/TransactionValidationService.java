package com.finance.app.validation.service;

import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.domain.dto.TransactionUpdateDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.validation.step.transaction.AmountValidation;
import com.finance.app.validation.step.transaction.TypeValidation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidationService extends ValidationService {
    private final ValidationStep<TransactionDto> validationPath;

    TransactionValidationService(Validator validator) {
        super(validator);
        this.validationPath =
                new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero()
                        .linkWith(new AmountValidation.StepValidIfDepositHasAmountMoreThenZero())
                        .linkWith(new TypeValidation.StepValidIfTypeIsTransfer())
                        .linkWith(new TypeValidation.StepValidIfTypeIsNotTransfer());
    }

    public ValidationResult validate(TransactionDto toValidate) {
        final var result = this.validateConstraint(toValidate);
        if (result.notValid())
            return result;

        return this.validationPath.validate(toValidate);
    }

    public ValidationResult validate(TransactionUpdateDto toValidate) {
        return this.validateConstraint(toValidate);
    }
}
