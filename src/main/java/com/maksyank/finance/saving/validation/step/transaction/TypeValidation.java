package com.maksyank.finance.saving.validation.step.transaction;

import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.domain.enums.TransactionType;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;

import java.math.BigDecimal;

public class TypeValidation {

    public static class StepValidIfTypeIsTransfer extends ValidationStep<TransactionDto> {
        @Override
        public ValidationResult validate(TransactionDto toValidate) {
            if (toValidate.type().equals(TransactionType.TRANSFER)) {
                if (toValidate.fromIdGoal() == null || toValidate.toIdGoal() == null) {
                    return ValidationResult.invalid("If the transaction is TRANSFER then please specify " +
                            "fields like fromIdGoal and toIdGoal.");
                }

                if (toValidate.amount().compareTo(BigDecimal.ZERO) <= 0) {
                    return ValidationResult.invalid("Amount of Transfer must be greater than zero.");
                }
            }
            return this.checkNext(toValidate);
        }
    }

    public static class StepValidIfTypeIsNotTransfer extends ValidationStep<TransactionDto> {
        @Override
        public ValidationResult validate(TransactionDto toValidate) {
            if (toValidate.type() != TransactionType.TRANSFER) {
                if (toValidate.fromIdGoal() != null || toValidate.toIdGoal() != null) {
                    return ValidationResult.invalid("If the transaction is not TRANSFER then please not specify " +
                            "fields like fromIdGoal and toIdGoal.");
                }
            }
            return this.checkNext(toValidate);
        }
    }
}
