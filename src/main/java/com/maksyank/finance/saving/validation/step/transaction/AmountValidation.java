package com.maksyank.finance.saving.validation.step.transaction;

import com.maksyank.finance.saving.domain.enums.TransactionType;
import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;

import java.math.BigDecimal;

public class AmountValidation {
    public static class StepValidIfScaleOneOrTwo extends ValidationStep<TransactionDto> {
        @Override
        public ValidationResult validate(TransactionDto toValidate) {
            if (0 <= toValidate.amount().scale() && toValidate.amount().scale() <= 2)
                return this.checkNext(toValidate);

            return ValidationResult.invalid("The 'balance' field must contain one or two digits after a decimal point.");
        }
    }
    public static class StepValidIfDepositHasAmountMoreThenZero extends ValidationStep<TransactionDto> {
        @Override
        public ValidationResult validate(TransactionDto toValidate) {
            if ((toValidate.type() == TransactionType.DEPOSIT)) {
                if ((toValidate.amount().compareTo(BigDecimal.ZERO) < 0) || (toValidate.amount().compareTo(BigDecimal.ZERO) == 0)) {
                    return ValidationResult.invalid("When transaction type is DEPOSIT then " +
                            "the 'balance' field must contain positive value.");
                }
            }
            return this.checkNext(toValidate);
        }
    }

    public static class StepValidIfWithdrawHasAmountLessThenZero extends ValidationStep<TransactionDto> {
        @Override
        public ValidationResult validate(TransactionDto toValidate) {
            if ((toValidate.type() == TransactionType.WITHDRAW)) {
                if ((toValidate.amount().compareTo(BigDecimal.ZERO) > 0) || (toValidate.amount().compareTo(BigDecimal.ZERO) == 0)) {
                    return ValidationResult.invalid("When transaction type is WITHDRAW then, " +
                            "the 'balance' field must contain negative value.");
                }
            }
            return this.checkNext(toValidate);
        }
    }
}