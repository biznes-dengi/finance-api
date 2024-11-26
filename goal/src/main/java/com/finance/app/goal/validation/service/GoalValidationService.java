package com.finance.app.goal.validation.service;

import com.finance.app.goal.domain.dto.GoalDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.validation.step.goal.DeadlineValidation;
import com.finance.app.validation.step.goal.ImageTypeValidation;
import com.finance.app.validation.step.goal.TargetAmountValidation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class GoalValidationService extends ValidationService {
    private final ValidationStep<GoalDto> validationPath;

    GoalValidationService(Validator validator) {
        super(validator);
        this.validationPath = new TargetAmountValidation.StepValidIfScaleOneOrTwo()
                .linkWith(new TargetAmountValidation.StepValidIfPositive())
                .linkWith(new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount())
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate())
                .linkWith(new ImageTypeValidation.StepValidIfNotExistWithoutImage());
    }

    public ValidationResult validate(GoalDto toValidate) {
        final var result = this.validateConstraint(toValidate);
        if (result.notValid()) return result;

        return this.validationPath.validate(toValidate);
    }
}
