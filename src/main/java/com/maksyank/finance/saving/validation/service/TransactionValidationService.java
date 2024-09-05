package com.maksyank.finance.saving.validation.service;

import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.domain.dto.TransactionUpdateDto;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;
import com.maksyank.finance.saving.validation.step.transaction.AmountValidation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidationService extends ValidationService {
    private final ValidationStep<TransactionDto> validationPath;

    TransactionValidationService(Validator validator) {
        super(validator);
        this.validationPath = new AmountValidation.StepValidIfScaleOneOrTwo()
                .linkWith(new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero());
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
