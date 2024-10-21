package com.finance.app.process;

import com.finance.app.generator.GeneratorDataBoardGoal;
import com.finance.app.dao.BoardGoalDao;
import com.finance.app.exception.NotFoundException;
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
public class BoardGoalProcessTest {
    @Mock
    private BoardGoalDao boardGoalDao;
    @InjectMocks
    private BoardGoalProcess testObj;

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be positive")
    void testUpdateBoardBalance_01() throws NotFoundException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.TEN);

        // Then
        assertEquals(BigDecimal.TEN, response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be negative")
    void testUpdateBoardBalance_02() throws NotFoundException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.valueOf(-10.00));

        // Then
        assertEquals(BigDecimal.valueOf(-10.00), response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be ZERO")
    void testUpdateBoardBalance_03() throws NotFoundException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(anyInt(), BigDecimal.ZERO);

        // Then
        assertEquals(BigDecimal.ZERO, response.getBoardBalance());
    }
}
