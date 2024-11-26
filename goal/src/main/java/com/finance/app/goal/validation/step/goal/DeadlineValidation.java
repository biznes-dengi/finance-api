package com.finance.app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;

import java.time.LocalDate;

/**
 * If the field is NULL then pass: TRUE
 */
public class DeadlineValidation {
    public static class StepValidIfItNotExistsWithoutTargetAmount extends ValidationStep<GoalDto> {
        @Override
        public ValidationResult validate(GoalDto toValidate) {
            if (toValidate.deadline() == null)
                return this.checkNext(toValidate);

            if (toValidate.targetAmount() == null) {
                return ValidationResult.invalid("The 'deadline' field can not exist without field 'targetAmount'");
            }
            return this.checkNext(toValidate);
        }
    }
    public static class StepValidIfDeadlineGreaterThenCurrentDate extends ValidationStep<GoalDto> {
        @Override
        public ValidationResult validate(GoalDto toValidate) {
            if (toValidate.deadline() == null)
                return this.checkNext(toValidate);

            LocalDate currentDate = LocalDate.now();
            if (toValidate.deadline().isBefore(currentDate) || toValidate.deadline().isEqual(currentDate)) {
                return ValidationResult.invalid("The 'deadline' field must contain at least the next day.");
            }
            return this.checkNext(toValidate);
        }
    }
}
