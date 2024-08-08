package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.service.GeneratorDataTransaction;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import com.maksyank.finance.saving.service.persistence.TransactionPersistence;
import com.maksyank.finance.saving.service.validation.TransactionValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionProcessTest {
    @Mock
    private TransactionPersistence transactionPersistence;
    @Mock
    private SavingPersistence savingPersistence;
    @Mock
    private SavingProcess savingProcess;
    @Mock
    private TransactionValidationService transactionValidationService;
    @InjectMocks
    private TransactionProcess transactionProcess;

    @Test
    @DisplayName("Check if it will be found transaction by id")
    void testFindTransactions_01() throws NotFoundException {
        // Given
        final int expectedId = 102;
        final int mockedSavingId = 1;
        final int mockerUserId = 1;
        final var saving = GeneratorDataTransaction.getTestData_testFindTransactions_01();

        // When
        when(savingPersistence.findByIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        final var result = this.transactionProcess.processGetById(expectedId, mockedSavingId, mockerUserId);

        // Then
        assertEquals(expectedId, result.id());
    }

    @Test
    @DisplayName("Check if it will throw NotFoundException, if it will search transaction by not exist id")
    void testFindTransactions_02() throws NotFoundException {
        // Given
        final int expectedId = 11;
        final int mockedSavingId = 1;
        final int mockerUserId = 1;
        final var saving = GeneratorDataTransaction.getTestData_testFindTransactions_02();

        // When
        when(savingPersistence.findByIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);

        // Then
        assertThrows(NotFoundException.class,
                () -> this.transactionProcess.processGetById(expectedId, mockedSavingId, mockerUserId));
    }
}
