package com.finance.app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.generator.GeneratorDataGoal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageTypeValidationTest {

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType is null & Image isn't null")
    public void testImageTypeValidationStep_01() {
        // Given
        ValidationStep<GoalDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var goalToValid = GeneratorDataGoal.getTestData_testImageTypeValidationStep_01();

        // When
        final var response = stepImage.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType isn't null & Image is null")
    public void testImageTypeValidationStep_02() {
        // Given
        ValidationStep<GoalDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var goalToValid = GeneratorDataGoal.getTestData_testImageTypeValidationStep_02();

        // When
        final var response = stepImage.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType is null & Image is null")
    public void testImageTypeValidationStep_03() {
        // Given
        ValidationStep<GoalDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var goalToValid = GeneratorDataGoal.getTestData_testImageTypeValidationStep_03();

        // When
        final var response = stepImage.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test ImageType step, check if ImageType isn't null & Image isn't null")
    public void testImageTypeValidationStep_04() {
        // Given
        ValidationStep<GoalDto> stepImage = new ImageTypeValidation.StepValidIfNotExistWithoutImage();
        final var goalToValid = GeneratorDataGoal.getTestData_testImageTypeValidationStep_04();

        // When
        final var response = stepImage.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }
}
