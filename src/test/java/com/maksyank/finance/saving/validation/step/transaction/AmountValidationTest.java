package com.maksyank.finance.saving.validation.step.transaction;

import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.utility.generator.GeneratorDataTransaction;
import com.maksyank.finance.saving.validation.step.ValidationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmountValidationTest {

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is DEPOSIT and fromGoalAmount is less then zero")
    public void testAmountValidationStep_04() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfDepositHasAmountMoreThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_04();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is DEPOSIT then the 'fromGoalAmount' field must contain positive value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is DEPOSIT and fromGoalAmount is zero")
    public void testAmountValidationStep_05() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfDepositHasAmountMoreThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_05();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is DEPOSIT then the 'fromGoalAmount' field must contain positive value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is DEPOSIT and fromGoalAmount is greater then zero")
    public void testAmountValidationStep_06() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfDepositHasAmountMoreThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_06();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is WITHDRAW and fromGoalAmount is less then zero")
    public void testAmountValidationStep_07() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_07();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is WITHDRAW and fromGoalAmount is zero")
    public void testAmountValidationStep_08() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_08();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is WITHDRAW then, the 'fromGoalAmount' field must contain negative value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'fromGoalAmount' is correct if type of transaction is WITHDRAW and fromGoalAmount is greater then zero")
    public void testAmountValidationStep_09() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_09();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is WITHDRAW then, the 'fromGoalAmount' field must contain negative value.", response.errorMsg());
    }


}
