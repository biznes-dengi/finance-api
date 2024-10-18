package com.maksyank.finance.unit.process;

import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.process.TransactionProcess;
import com.maksyank.finance.unit.GeneratorDataTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionProcessTest {
    @Mock
    private SavingDao savingDao;
    @InjectMocks
    private TransactionProcess transactionProcess;
    @Mock
    private TransactionMapper transactionMapper;

    @Test
    @DisplayName("Check if it will be found transaction by id")
    void testFindTransactions_01() throws NotFoundException {
        // Given
        final int expectedId = 102;
        final int mockedSavingId = 1;
        final int mockerUserId = 1;
        final var saving = GeneratorDataTransaction.getTestData_testFindTransactions_01();

        // When
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(transactionMapper.transactionToTransactionResponse(any())).thenAnswer((invocationOnMock) -> {
            final var responseTransaction = (Transaction) invocationOnMock.getArguments()[0];
            return new TransactionResponse(responseTransaction.getId(), responseTransaction.getType(),
                    responseTransaction.getDescription() , responseTransaction.getDealDate(), responseTransaction.getAmount());
        });
        final var result = transactionProcess.processGetById(expectedId, mockedSavingId, mockerUserId);

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
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);

        // Then
        assertThrows(NotFoundException.class,
                () -> this.transactionProcess.processGetById(expectedId, mockedSavingId, mockerUserId));
    }
}
