package com.maksyank.finance.unit.process;

import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.process.SavingProcess;
import com.maksyank.finance.unit.GeneratorDataSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavingProcessTest {
    @Mock private SavingDao savingDaoMock;
    @InjectMocks private SavingProcess testObj;

    @Test
    @DisplayName("Test updateSavingBalance, if a new transaction arrived but state of saving already is OVERDUE")
    void testUpdateSavingBalance_01() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_01();

        // When
        when(savingDaoMock.fetchSavingById(anyInt(), anyInt())).thenReturn(saving);
        when(savingDaoMock.createSaving(any())).thenReturn(saving);
        final var response = this.testObj.updateSavingBalance(BigDecimal.TEN, anyInt(), anyInt());

        // Then
        assertEquals(SavingState.OVERDUE, response.getState());
        assertEquals(0, BigDecimal.valueOf(130.2).compareTo(response.getSavingBalance()));
    }

    @Test
    @DisplayName("Test updateSavingBalance, if balance will be more that target balance")
    void testUpdateSavingBalance_02() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_02();

        // When
        when(savingDaoMock.fetchSavingById(anyInt(), anyInt())).thenReturn(saving);
        when(savingDaoMock.createSaving(any())).thenReturn(saving);
        final var response = this.testObj.updateSavingBalance(BigDecimal.valueOf(100.6), anyInt(), anyInt());

        // Then
        assertEquals(SavingState.ACHIEVED, response.getState());
        assertEquals(0, BigDecimal.valueOf(220.8).compareTo(response.getSavingBalance()));
    }

    @Test
    @DisplayName("Test updateSavingBalance, if a new withdraw arrived but state of saving already is ACHIEVED")
    void testUpdateSavingBalance_03() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateState_03();

        // When
        when(savingDaoMock.fetchSavingById(anyInt(), anyInt())).thenReturn(saving);
        when(savingDaoMock.createSaving(any())).thenReturn(saving);
        final var response = this.testObj.updateSavingBalance(BigDecimal.valueOf(-100.6), anyInt(), anyInt());

        // Then
        assertEquals(SavingState.ACTIVE, response.getState());
        assertEquals(0, BigDecimal.valueOf(60.3).compareTo(response.getSavingBalance()));
    }

    @Test
    @DisplayName("Test updateBalance, if a new transaction arrived but state of saving already is OVERDUE")
    void testUpdateSavingBalance_04() throws NotFoundException {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testUpdateBalance_01();

        // When
        when(savingDaoMock.fetchSavingById(anyInt(), anyInt())).thenReturn(saving);
        when(savingDaoMock.createSaving(any())).thenReturn(saving);

        // Then
        var response = this.testObj
                .updateSavingBalance(BigDecimal.valueOf(932.02), anyInt(), anyInt());
        assertEquals(BigDecimal.valueOf(932.02), response.getSavingBalance());

        this.testObj.updateSavingBalance(BigDecimal.valueOf(-150.22), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(781.8).compareTo(response.getSavingBalance()));

        this.testObj.updateSavingBalance(BigDecimal.valueOf(-1150), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(-368.2).compareTo(response.getSavingBalance()));

        this.testObj.updateSavingBalance(BigDecimal.valueOf(1930.1), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(1561.9).compareTo(response.getSavingBalance()));

        this.testObj.updateSavingBalance(BigDecimal.valueOf(-1561.91), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(-0.01).compareTo(response.getSavingBalance()));
    }
}
