package app.process;

import com.finance.app.service.AccountProcess;
import com.finance.app.dao.GoalDao;
import com.finance.app.domain.enums.GoalState;
import app.generator.GeneratorDataGoal;
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
    private GoalDao goalDao;
    @InjectMocks
    private UtilityProcess utilityProcess;

    @Test
    @DisplayName("Test scheduledCheckGoalsIfOverdue. Check if will change a status of goals to OVERDUE")
    void testScheduledCheckGoalsIfOverdue_01() {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testScheduledCheckGoalsIfOverdue_01();

        // When
        when(this.accountProcess.getListIdsOfUsers())
                .thenReturn(List.of(1));
        when(goalDao.fetchGoalsByStateAndDeadlineIsNotNull(GoalState.ACTIVE, 1))
                .thenReturn(goal);

        utilityProcess.scheduledCheckGoalsIfOverdue();

        // Then
        assertEquals(GoalState.OVERDUE, goal.get(0).getState());
    }

    @Test
    @DisplayName("Test scheduledCheckGoalsIfOverdue. Check if will not change a status of goals to OVERDUE")
    void testScheduledCheckGoalsIfOverdue_02() {
        // Given
        final var goal = GeneratorDataGoal.getTestData_testScheduledCheckGoalsIfOverdue_02();

        // When
        when(this.accountProcess.getListIdsOfUsers())
                .thenReturn(List.of(1));
        when(goalDao.fetchGoalsByStateAndDeadlineIsNotNull(GoalState.ACTIVE, 1))
                .thenReturn(goal);

        utilityProcess.scheduledCheckGoalsIfOverdue();

        // Then
        assertEquals(GoalState.ACTIVE, goal.get(0).getState());
    }
}
