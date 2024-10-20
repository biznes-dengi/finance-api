package com.finance.app.validation.step.saving;

import com.finance.app.domain.dto.SavingDto;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.generator.GeneratorDataSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageTypeValidationTest {

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType is null & Image isn't null")
    public void testImageTypeValidationStep_01() {
        // Given
        ValidationStep<SavingDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var savingToValid = GeneratorDataSaving.getTestData_testImageTypeValidationStep_01();

        // When
        final var response = stepImage.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType isn't null & Image is null")
    public void testImageTypeValidationStep_02() {
        // Given
        ValidationStep<SavingDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var savingToValid = GeneratorDataSaving.getTestData_testImageTypeValidationStep_02();

        // When
        final var response = stepImage.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType is null & Image is null")
    public void testImageTypeValidationStep_03() {
        // Given
        ValidationStep<SavingDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var savingToValid = GeneratorDataSaving.getTestData_testImageTypeValidationStep_03();

        // When
        final var response = stepImage.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType isn't null & Image isn't null")
    public void testImageTypeValidationStep_04() {
        // Given
        ValidationStep<SavingDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var savingToValid = GeneratorDataSaving.getTestData_testImageTypeValidationStep_04();

        // When
        final var response = stepImage.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }
}
