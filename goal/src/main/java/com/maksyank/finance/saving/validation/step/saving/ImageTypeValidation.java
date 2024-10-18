package com.maksyank.finance.saving.validation.step.saving;

import com.maksyank.finance.saving.domain.dto.SavingDto;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;

public class ImageTypeValidation {
    public static class StepValidIfNotExistWithoutImage extends ValidationStep<SavingDto> {
        @Override
        public ValidationResult validate(SavingDto toValidate) {
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
