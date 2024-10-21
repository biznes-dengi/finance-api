package com.finance.app.validation.step.transaction;

import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.domain.enums.TransactionType;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;

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
