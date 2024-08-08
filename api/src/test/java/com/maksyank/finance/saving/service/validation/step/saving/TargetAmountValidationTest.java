package com.maksyank.finance.saving.service.validation.step.saving;

import com.maksyank.finance.saving.dto.SavingDto;
import com.maksyank.finance.saving.service.GeneratorDataSaving;
import com.maksyank.finance.saving.service.validation.step.ValidationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TargetAmountValidationTest {

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has all valid data")
    public void testTargetAmountValidationStep_AllValidData() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo()
                .linkWith(new TargetAmountValidation.StepValidIfPositive());
        final var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_AllValidData();

        // When
        final var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if NULL value will pass")
    public void testTargetAmountValidationStep_01() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive()
                .linkWith(new TargetAmountValidation.StepValidIfScaleOneOrTwo());
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_01();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is 0")
    public void testTargetAmountValidationStep_02() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_02();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain positive number.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is less than 0")
    public void testTargetAmountValidationStep_03() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_03();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain positive number.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has no digits after comma")
    public void testTargetAmountValidationStep_04() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_04();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has one digit after comma")
    public void testTargetAmountValidationStep_05() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_05();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has three digits after comma")
    public void testTargetAmountValidationStep_06() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_06();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain one or two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is more than 0")
    public void testTargetAmountValidationStep_08() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_08();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }
}
