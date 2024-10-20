package com.finance.app.validation.step.transaction;

import com.finance.app.generator.GeneratorDataTransaction;
import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.validation.step.ValidationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeValidationTest {

    @Test
    @DisplayName("Test StepValidIfTypeIsTransfer - positive")
    public void testTypeValidation_01() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_01();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
       assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Test StepValidIfTypeIsTransfer - negative, not specify fromIdGoal and toIdGoal")
    public void testTypeValidation_02() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_02();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Test StepValidIfTypeIsTransfer - negative, amount is zero")
    public void testTypeValidation_03() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_03();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Test StepValidIfTypeIsTransfer - negative, amount is negative")
    public void testTypeValidation_04() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_04();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Test StepValidIfTypeIsNotTransfer - positive")
    public void testTypeValidation_05() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsNotTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_05();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Test StepValidIfTypeIsNotTransfer - negative, specify fromIdGoal and toIdGoal")
    public void testTypeValidation_06() {
        // Given
        ValidationStep<TransactionDto> stepTypeValidation = new TypeValidation.StepValidIfTypeIsNotTransfer();
        final var dtoToTest = GeneratorDataTransaction.getTestData_testTypeValidation_06();

        // When
        final var result = stepTypeValidation.validate(dtoToTest);

        // Then
        assertFalse(result.isValid());
    }
}
