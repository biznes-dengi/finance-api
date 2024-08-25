package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.service.GeneratorDataTransaction;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDepositProcessTest {
    @Mock
    private SavingPersistence savingPersistence;
    @InjectMocks
    private TransactionDepositProcess transactionDepositProcess;

    @Test
    @DisplayName("Check if fund deposits will be summed correctly")
    void testProcessGetDepositAmountByMonth_01() throws NotFoundException, ValidationException {
        // Given
        final int year = 2023;
        final int month = 3;
        final var saving = GeneratorDataTransaction.getTestData_testProcessGetDepositAmountByMonth_01();

        // When
        when(savingPersistence.findByIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        final var result = transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(1, year, month, 1));

        // Then
        assertEquals(BigDecimal.valueOf(3447.22), result);
    }

    @Test
    @DisplayName("Check if it will throw NotFoundException, if the month do not have any deposits")
    void testProcessGetDepositAmountByMonth_02() throws NotFoundException {
        // Given
        final int year = 2023;
        final int month = 8;
        final var saving = GeneratorDataTransaction.getTestData_testProcessGetDepositAmountByMonth_02();

        // When
        when(savingPersistence.findByIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);

        // Then
        assertThrows(NotFoundException.class,
                () -> transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(1, year, month, 1)));
    }
}
