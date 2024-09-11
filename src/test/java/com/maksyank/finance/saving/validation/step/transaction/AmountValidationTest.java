package com.maksyank.finance.saving.validation.step.transaction;

import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.GeneratorDataTransaction;
import com.maksyank.finance.saving.validation.step.ValidationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmountValidationTest {
    @Test
    @DisplayName(value = "Test Amount step, check if the balance has no digits after comma")
    public void testAmountValidationStep_01() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfScaleOneOrTwo();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_01();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Amount step, check if the balance has three digits after comma")
    public void testAmountValidationStep_02() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfScaleOneOrTwo();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_02();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'balance' field must contain one or two digits after a decimal point.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, check if the balance has two digits after comma")
    public void testAmountValidationStep_03() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfScaleOneOrTwo();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_03();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'balance' is correct if type of transaction is DEPOSIT and balance is less then zero")
    public void testAmountValidationStep_04() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfDepositHasAmountMoreThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_04();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is DEPOSIT then the 'balance' field must contain positive value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'balance' is correct if type of transaction is DEPOSIT and balance is zero")
    public void testAmountValidationStep_05() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfDepositHasAmountMoreThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_05();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is DEPOSIT then the 'balance' field must contain positive value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'balance' is correct if type of transaction is DEPOSIT and balance is greater then zero")
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
            "check if the field 'balance' is correct if type of transaction is WITHDRAW and balance is less then zero")
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
            "check if the field 'balance' is correct if type of transaction is WITHDRAW and balance is zero")
    public void testAmountValidationStep_08() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_08();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is WITHDRAW then, the 'balance' field must contain negative value.", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Amount step, " +
            "check if the field 'balance' is correct if type of transaction is WITHDRAW and balance is greater then zero")
    public void testAmountValidationStep_09() {
        // Given
        ValidationStep<TransactionDto> stepAmount = new AmountValidation.StepValidIfWithdrawHasAmountLessThenZero();
        final var transactionDtoToValid = GeneratorDataTransaction.getTestData_testAmountValidationStep_09();

        // When
        final var response = stepAmount.validate(transactionDtoToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("When transaction type is WITHDRAW then, the 'balance' field must contain negative value.", response.errorMsg());
    }


}
