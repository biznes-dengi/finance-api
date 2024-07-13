package com.maksyank.finance.saving.service.validation;

import com.maksyank.finance.saving.dto.SavingDto;
import com.maksyank.finance.saving.service.GeneratorDataSaving;
import com.maksyank.finance.saving.service.validation.step.ValidationStep;
import com.maksyank.finance.saving.service.validation.step.saving.TargetAmountValidationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingValidationTest {

    @Test
    @DisplayName(value = "Test TargetAmount step, check if NULL value will pass")
    public void testTargetAmountValidationStep_01() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
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
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
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
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
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
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_04();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has one digit after comma")
    public void testTargetAmountValidationStep_05() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_05();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has three digits after comma")
    public void testTargetAmountValidationStep_06() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_06();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has all valid data")
    public void testTargetAmountValidationStep_07() {
        // Given
        ValidationStep<SavingDto> stepTargetAmount = new TargetAmountValidationStep();
        var savingToValid = GeneratorDataSaving.getTestData_testTargetAmountValidationStep_07();

        // When
        var response = stepTargetAmount.validate(savingToValid);

        // Then
        assertTrue(response.isValid());
    }


}
