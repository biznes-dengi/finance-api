package com.maksyank.finance.saving.validation.step.saving;

import com.maksyank.finance.saving.domain.dto.SavingDto;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.saving.validation.step.ValidationStep;

public class ImageTypeValidation {
    public static class StepValidIfNotExistWithoutImage extends ValidationStep<SavingDto> {
        @Override
        public ValidationResult validate(SavingDto toValidate) {
            if (toValidate.image() == null && toValidate.imageType() != null || toValidate.image() != null && toValidate.imageType() == null) {
                return ValidationResult.invalid("The 'ImageType' field can not exist without field 'Image'");
            }

            return this.checkNext(toValidate);
        }
    }
}
