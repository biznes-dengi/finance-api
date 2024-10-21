package com.finance.app.process;

import com.finance.app.dao.GoalDao;
import com.finance.app.domain.enums.GoalState;
import com.finance.app.generator.GeneratorDataGoal;
import com.finance.app.exception.ParentException;
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
public class GoalProcessTest {
    @Mock private GoalDao goalDaoMock;
    @InjectMocks private GoalProcess testObj;

    @Test
    @DisplayName("Test updateGoalBalance, if a new transaction arrived but state of goal already is OVERDUE")
    void testUpdateGoalBalance_01() throws ParentException {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testUpdateState_01();

        // When
        when(goalDaoMock.fetchGoalById(anyInt(), anyInt())).thenReturn(goal);
        when(goalDaoMock.createGoal(any())).thenReturn(goal);
        final var response = this.testObj.updateGoalBalance(BigDecimal.TEN, anyInt(), anyInt());

        // Then
        assertEquals(GoalState.OVERDUE, response.getState());
        assertEquals(0, BigDecimal.valueOf(130.2).compareTo(response.getGoalBalance()));
    }

    @Test
    @DisplayName("Test updateGoalBalance, if balance will be more that target balance")
    void testUpdateGoalBalance_02() throws ParentException {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testUpdateState_02();

        // When
        when(goalDaoMock.fetchGoalById(anyInt(), anyInt())).thenReturn(goal);
        when(goalDaoMock.createGoal(any())).thenReturn(goal);
        final var response = this.testObj.updateGoalBalance(BigDecimal.valueOf(100.6), anyInt(), anyInt());

        // Then
        assertEquals(GoalState.ACHIEVED, response.getState());
        assertEquals(0, BigDecimal.valueOf(220.8).compareTo(response.getGoalBalance()));
    }

    @Test
    @DisplayName("Test updateGoalBalance, if a new withdraw arrived but state of goal already is ACHIEVED")
    void testUpdateGoalBalance_03() throws ParentException {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testUpdateState_03();

        // When
        when(goalDaoMock.fetchGoalById(anyInt(), anyInt())).thenReturn(goal);
        when(goalDaoMock.createGoal(any())).thenReturn(goal);
        final var response = this.testObj.updateGoalBalance(BigDecimal.valueOf(-100.6), anyInt(), anyInt());

        // Then
        assertEquals(GoalState.ACTIVE, response.getState());
        assertEquals(0, BigDecimal.valueOf(60.3).compareTo(response.getGoalBalance()));
    }

    @Test
    @DisplayName("Test updateBalance, if a new transaction arrived but state of goal already is OVERDUE")
    void testUpdateGoalBalance_04() throws ParentException {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testUpdateBalance_01();

        // When
        when(goalDaoMock.fetchGoalById(anyInt(), anyInt())).thenReturn(goal);
        when(goalDaoMock.createGoal(any())).thenReturn(goal);

        // Then
        var response = this.testObj
                .updateGoalBalance(BigDecimal.valueOf(932.02), anyInt(), anyInt());
        assertEquals(BigDecimal.valueOf(932.02), response.getGoalBalance());

        this.testObj.updateGoalBalance(BigDecimal.valueOf(-150.22), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(781.8).compareTo(response.getGoalBalance()));

        this.testObj.updateGoalBalance(BigDecimal.valueOf(-1150), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(-368.2).compareTo(response.getGoalBalance()));

        this.testObj.updateGoalBalance(BigDecimal.valueOf(1930.1), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(1561.9).compareTo(response.getGoalBalance()));

        this.testObj.updateGoalBalance(BigDecimal.valueOf(-1561.91), anyInt(), anyInt());
        assertEquals(0, BigDecimal.valueOf(-0.01).compareTo(response.getGoalBalance()));
    }
}
