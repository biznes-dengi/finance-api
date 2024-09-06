package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.GeneratorDataSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavingProcessTest {
    @Mock
    private SavingDao savingDao;
    @InjectMocks
    private SavingProcess savingProcess;

    @Test
    @DisplayName("Test updateBalance. If a new transaction arrived but state of saving already is OVERDUE")
    void testUpdateState_01() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_01();

        // When
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(savingDao.createSaving(Mockito.any())).thenReturn(saving);
        final var response = this.savingProcess.updateBalance(BigDecimal.TEN, Mockito.anyInt(), Mockito.anyInt());

        // Then
        assertEquals(SavingState.OVERDUE, response.getState());
        assertEquals(0, BigDecimal.valueOf(130.2).compareTo(response.getBalance()));
    }

    @Test
    @DisplayName("Test updateBalance. If balance will be more that target amount")
    void testUpdateState_02() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_02();

        // When
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(savingDao.createSaving(Mockito.any())).thenReturn(saving);
        final var response = this.savingProcess.updateBalance(BigDecimal.valueOf(100.6), Mockito.anyInt(), Mockito.anyInt());

        // Then
        assertEquals(SavingState.ACHIEVED, response.getState());
        assertEquals(0, BigDecimal.valueOf(220.8).compareTo(response.getBalance()));
    }

    @Test
    @DisplayName("Test updateBalance. If a new withdraw arrived but state of saving already is ACHIEVED")
    void testUpdateState_03() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_03();

        // When
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(savingDao.createSaving(Mockito.any())).thenReturn(saving);
        final var response = this.savingProcess.updateBalance(BigDecimal.valueOf(-100.6), Mockito.anyInt(), Mockito.anyInt());

        // Then
        assertEquals(SavingState.ACTIVE, response.getState());
        assertEquals(0, BigDecimal.valueOf(60.3).compareTo(response.getBalance()));
    }

    @Test
    @DisplayName("Test updateBalance. If a new transaction arrived but state of saving already is OVERDUE")
    void testUpdateBalance_01() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateBalance_01();

        // When
        when(savingDao.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(savingDao.createSaving(Mockito.any())).thenReturn(saving);

        // Then
        var response = this.savingProcess
                .updateBalance(BigDecimal.valueOf(932.02), Mockito.anyInt(), Mockito.anyInt());
        assertEquals(BigDecimal.valueOf(932.02), response.getBalance());

        this.savingProcess.updateBalance(BigDecimal.valueOf(-150.22), Mockito.anyInt(), Mockito.anyInt());
        assertEquals(0, BigDecimal.valueOf(781.8).compareTo(response.getBalance()));

        this.savingProcess.updateBalance(BigDecimal.valueOf(-1150), Mockito.anyInt(), Mockito.anyInt());
        assertEquals(0, BigDecimal.valueOf(-368.2).compareTo(response.getBalance()));

        this.savingProcess.updateBalance(BigDecimal.valueOf(1930.1), Mockito.anyInt(), Mockito.anyInt());
        assertEquals(0, BigDecimal.valueOf(1561.9).compareTo(response.getBalance()));

        this.savingProcess.updateBalance(BigDecimal.valueOf(-1561.91), Mockito.anyInt(), Mockito.anyInt());
        assertEquals(0, BigDecimal.valueOf(-0.01).compareTo(response.getBalance()));
    }
}
