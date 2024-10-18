package com.maksyank.finance.unit.process;

import com.maksyank.finance.saving.dao.BoardSavingDao;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.process.BoardSavingProcess;
import com.maksyank.finance.unit.GeneratorDataBoardSaving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardSavingProcessTest {
    @Mock
    private BoardSavingDao boardSavingDao;
    @InjectMocks
    private BoardSavingProcess testObj;

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be positive")
    void testUpdateBoardBalance_01() throws NotFoundException {
        // Given
        final var boardSaving = GeneratorDataBoardSaving.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardSavingDao.fetchBoardSavingById(anyInt())).thenReturn(boardSaving);
        when(boardSavingDao.createBoardSaving(boardSaving)).thenReturn(boardSaving);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.TEN);

        // Then
        assertEquals(BigDecimal.TEN, response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be negative")
    void testUpdateBoardBalance_02() throws NotFoundException {
        // Given
        final var boardSaving = GeneratorDataBoardSaving.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardSavingDao.fetchBoardSavingById(anyInt())).thenReturn(boardSaving);
        when(boardSavingDao.createBoardSaving(boardSaving)).thenReturn(boardSaving);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.valueOf(-10.00));

        // Then
        assertEquals(BigDecimal.valueOf(-10.00), response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be ZERO")
    void testUpdateBoardBalance_03() throws NotFoundException {
        // Given
        final var boardSaving = GeneratorDataBoardSaving.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardSavingDao.fetchBoardSavingById(anyInt())).thenReturn(boardSaving);
        when(boardSavingDao.createBoardSaving(boardSaving)).thenReturn(boardSaving);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.ZERO);

        // Then
        assertEquals(BigDecimal.ZERO, response.getBoardBalance());
    }
}
