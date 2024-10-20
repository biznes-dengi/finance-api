package com.finance.app.validation.service;

import com.finance.app.domain.dto.SavingDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.validation.step.saving.DeadlineValidation;
import com.finance.app.validation.step.saving.ImageTypeValidation;
import com.finance.app.validation.step.saving.TargetAmountValidation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class SavingValidationService extends ValidationService {
    private final ValidationStep<SavingDto> validationPath;

    SavingValidationService(Validator validator) {
        super(validator);
        this.validationPath = new TargetAmountValidation.StepValidIfScaleOneOrTwo()
                .linkWith(new TargetAmountValidation.StepValidIfPositive())
                .linkWith(new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount())
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate())
                .linkWith(new ImageTypeValidation.StepValidIfNotExistWithoutImage());
    }

    public ValidationResult validate(SavingDto toValidate) {
        final var result = this.validateConstraint(toValidate);
        if (result.notValid()) return result;

        return this.validationPath.validate(toValidate);
    }
}
