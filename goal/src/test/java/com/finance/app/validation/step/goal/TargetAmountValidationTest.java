package com.finance.app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.step.ValidationStep;
import com.finance.app.generator.GeneratorDataGoal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TargetAmountValidationTest {

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has all valid data")
    public void testTargetAmountValidationStep_AllValidData() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo()
                .linkWith(new TargetAmountValidation.StepValidIfPositive());
        final var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_AllValidData();

        // When
        final var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if NULL value will pass")
    public void testTargetAmountValidationStep_01() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive()
                .linkWith(new TargetAmountValidation.StepValidIfScaleOneOrTwo());
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_01();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is 0")
    public void testTargetAmountValidationStep_02() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_02();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain positive number.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is less than 0")
    public void testTargetAmountValidationStep_03() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_03();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain positive number.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has no digits after comma")
    public void testTargetAmountValidationStep_04() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_04();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has one digit after comma")
    public void testTargetAmountValidationStep_05() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_05();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount has three digits after comma")
    public void testTargetAmountValidationStep_06() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfScaleOneOrTwo();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_06();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'target_amount' field must contain one or two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test TargetAmount step, check if targetAmount is more than 0")
    public void testTargetAmountValidationStep_08() {
        // Given
        ValidationStep<GoalDto> stepTargetAmount = new TargetAmountValidation.StepValidIfPositive();
        var goalToValid = GeneratorDataGoal.getTestData_testTargetAmountValidationStep_08();

        // When
        var response = stepTargetAmount.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }
}
