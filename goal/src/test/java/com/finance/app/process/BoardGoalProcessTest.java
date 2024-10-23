package com.finance.app.process;

import com.finance.app.domain.enums.TransactionType;
import com.finance.app.exception.ParentException;
import com.finance.app.generator.GeneratorDataBoardGoal;
import com.finance.app.dao.BoardGoalDao;
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
    void testUpdateBoardBalance_01() throws ParentException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(TransactionType.DEPOSIT, BigDecimal.TEN, anyInt());

        // Then
        assertEquals(BigDecimal.TEN, response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance, check if new transaction will have WITHDRAW type and balance is ZERO")
    void testUpdateBoardBalance_02() throws ParentException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(TransactionType.WITHDRAW, BigDecimal.TEN, anyInt());

        // Then
        assertEquals(BigDecimal.valueOf(-10), response.getBoardBalance());
    }

    @Test
    @DisplayName("Test updateBoardBalance. Check if new value will be ZERO")
    void testUpdateBoardBalance_03() throws ParentException {
        // Given
        final var boardGoal = GeneratorDataBoardGoal.getTestData_testUpdateBoardBalance_01_02_03();

        // When
        when(boardGoalDao.fetchBoardGoalById(anyInt())).thenReturn(boardGoal);
        when(boardGoalDao.createBoardGoal(boardGoal)).thenReturn(boardGoal);
        final var response = testObj.updateBoardBalance(TransactionType.DEPOSIT, BigDecimal.ZERO, anyInt());

        // Then
        assertEquals(BigDecimal.ZERO, response.getBoardBalance());
    }
}
