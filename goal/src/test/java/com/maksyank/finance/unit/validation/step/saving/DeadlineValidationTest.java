package com.maksyank.finance.unit.validation.step.saving;

import com.maksyank.finance.saving.domain.dto.SavingDto;
import com.maksyank.finance.saving.validation.step.ValidationStep;
import com.maksyank.finance.saving.validation.step.saving.DeadlineValidation;
import com.maksyank.finance.unit.GeneratorDataSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineValidationTest {

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline has all valid data")
    public void testDeadlineValidationStep_AllValidData() {
        // Given
        ValidationStep<SavingDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount()
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate());
        final var savingToValid = GeneratorDataSaving.getTestData_testDeadlineValidationStep_AllValidData();

        // When
        final var response = stepDeadline.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if targetAmount is null & deadline exists")
    public void testDeadlineValidationStep_01() {
        // Given
        ValidationStep<SavingDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount();
        final var savingToValid = GeneratorDataSaving.getTestData_testDeadlineValidationStep_01();

        // When
        final var response = stepDeadline.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'deadline' field can not exist without field 'targetAmount'", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline is null")
    public void testDeadlineValidationStep_02() {
        // Given
        ValidationStep<SavingDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount()
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate());
        final var savingToValid = GeneratorDataSaving.getTestData_testDeadlineValidationStep_02();

        // When
        final var response = stepDeadline.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline is less than current day")
    public void testDeadlineValidationStep_03() {
        // Given
        ValidationStep<SavingDto> stepDeadline = new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate();
        final var savingToValid = GeneratorDataSaving.getTestData_testDeadlineValidationStep_03();

        // When
        final var response = stepDeadline.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'deadline' field must contain at least the next day.", response.errorMsg());
    }
}
