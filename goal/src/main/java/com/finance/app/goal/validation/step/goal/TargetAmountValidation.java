package com.finance.app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;

import java.math.BigDecimal;

/**
 * If the field is NULL then pass: TRUE
 */
public class TargetAmountValidation {
    public static class StepValidIfPositive extends ValidationStep<GoalDto> {
        @Override
        public ValidationResult validate(GoalDto toValidate) {
            if (toValidate.targetAmount() == null)
                return this.checkNext(toValidate);

            if (toValidate.targetAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ValidationResult.invalid("The 'target_amount' field must contain positive number.");
            }
            return this.checkNext(toValidate);
        }
    }
    public static class StepValidIfScaleOneOrTwo extends ValidationStep<GoalDto> {
        @Override
        public ValidationResult validate(GoalDto toValidate) {
            if (toValidate.targetAmount() == null)
                return this.checkNext(toValidate);

            if (0 <= toValidate.targetAmount().scale() && toValidate.targetAmount().scale() <= 2)
                return this.checkNext(toValidate);

            return ValidationResult.invalid("The 'target_amount' field must contain one or two digits after a decimal point.");
        }
    }
}