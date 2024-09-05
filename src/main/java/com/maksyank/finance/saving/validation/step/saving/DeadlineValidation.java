package com.maksyank.finance.saving.validation.step.saving;

import com.maksyank.finance.saving.domain.dto.SavingDto;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;

import java.time.LocalDate;

/**
 * If the field is NULL then pass: TRUE
 */
public class DeadlineValidation {
    public static class StepValidIfItNotExistsWithoutTargetAmount extends ValidationStep<SavingDto> {
        @Override
        public ValidationResult validate(SavingDto toValidate) {
            if (toValidate.deadline() == null)
                return this.checkNext(toValidate);

            if (toValidate.targetAmount() == null) {
                return ValidationResult.invalid("The 'deadline' field can not exist without field 'targetAmount'");
            }
            return this.checkNext(toValidate);
        }
    }
    public static class StepValidIfDeadlineGreaterThenCurrentDate extends ValidationStep<SavingDto> {
        @Override
        public ValidationResult validate(SavingDto toValidate) {
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
