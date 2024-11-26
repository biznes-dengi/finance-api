package com.finance.app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.step.ValidationStep;

public class ImageTypeValidation {
    public static class StepValidIfNotExistWithoutImage extends ValidationStep<GoalDto> {
        @Override
        public ValidationResult validate(GoalDto toValidate) {
            if (toValidate.image() == null)
                return this.checkNext(toValidate);

            if (toValidate.image().value() == null && toValidate.image().imageType() != null
                    || toValidate.image().value() != null && toValidate.image().imageType() == null) {
                return ValidationResult.invalid("ImageType must be entered together with value of image.");
            }

            return this.checkNext(toValidate);
        }
    }
}
