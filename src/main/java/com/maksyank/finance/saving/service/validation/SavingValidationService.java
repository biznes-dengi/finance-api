package com.maksyank.finance.saving.service.validation;

import com.maksyank.finance.saving.dto.SavingDto;
import com.maksyank.finance.saving.service.validation.step.ValidationStep;
import com.maksyank.finance.saving.service.validation.step.saving.DeadlineValidation;
import com.maksyank.finance.saving.service.validation.step.saving.ImageTypeValidation;
import com.maksyank.finance.saving.service.validation.step.saving.TargetAmountValidation;
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
