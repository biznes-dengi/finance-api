package com.finance.app.process;

import com.finance.app.service.AccountProcess;
import com.finance.app.dao.SavingDao;
import com.finance.app.domain.enums.SavingState;
import com.finance.app.generator.GeneratorDataSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtilityProcessTest {
    @Mock
    private AccountProcess accountProcess;
    @Mock
    private SavingDao savingDao;
    @InjectMocks
    private UtilityProcess utilityProcess;

    @Test
    @DisplayName("Test scheduledCheckSavingsIfOverdue. Check if will change a status of savings to OVERDUE")
    void testScheduledCheckSavingsIfOverdue_01() {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testScheduledCheckSavingsIfOverdue_01();

        // When
        when(this.accountProcess.getListIdsOfUsers())
                .thenReturn(List.of(1));
        when(savingDao.fetchSavingsByStateAndDeadlineIsNotNull(SavingState.ACTIVE, 1))
                .thenReturn(saving);

        utilityProcess.scheduledCheckSavingsIfOverdue();

        // Then
        assertEquals(SavingState.OVERDUE, saving.get(0).getState());
    }

    @Test
    @DisplayName("Test scheduledCheckSavingsIfOverdue. Check if will not change a status of savings to OVERDUE")
    void testScheduledCheckSavingsIfOverdue_02() {
        // Given
        final var saving = GeneratorDataSaving.getTestData_testScheduledCheckSavingsIfOverdue_02();

        // When
        when(this.accountProcess.getListIdsOfUsers())
                .thenReturn(List.of(1));
        when(savingDao.fetchSavingsByStateAndDeadlineIsNotNull(SavingState.ACTIVE, 1))
                .thenReturn(saving);

        utilityProcess.scheduledCheckSavingsIfOverdue();

        // Then
        assertEquals(SavingState.ACTIVE, saving.get(0).getState());
    }
}
