package com.maksyank.finance.saving.validation.step.transaction;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DecimalScale.DecimalScaleValidator.class)
public @interface DecimalScale {

    String message() default "The 'amount' field must contain at least [{value}] digits after a decimal point.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();

    class DecimalScaleValidator implements ConstraintValidator<DecimalScale, BigDecimal> {

        private int scale;

        @Override
        public void initialize(final DecimalScale constraintAnnotation) {
            this.scale = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(final BigDecimal value, final ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            return 0 <= value.scale() && value.scale() <= scale;
        }
    }
}
