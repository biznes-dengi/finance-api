package com.finance.app.goal.process;

import com.finance.app.account.process.AccountProcess;
import com.finance.app.goal.dao.GoalDao;
import com.finance.app.goal.domain.enums.GoalState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Async
@RequiredArgsConstructor
public class UtilityProcess {
    final private AccountProcess accountProcess;
    final private GoalDao goalDao;

    // TODO it's temporary impl, task in Notion
    // TODO there's bug with time zone, right now the impl only for one time zone
    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void scheduledCheckGoalsIfOverdue() {
        this.accountProcess.getListIdsOfUsers().stream()
                .map(userId -> goalDao.fetchGoalsByStateAndDeadlineIsNotNull(GoalState.ACTIVE, userId))
                .filter(listGoal -> !listGoal.isEmpty())
                .forEach(listGoal ->
                        listGoal.stream()
                                .filter(goal -> goal.getDeadline().isBefore(LocalDate.now()) ||
                                        goal.getDeadline().isEqual(LocalDate.now()))
                                .forEach(goal -> {
                                    goal.setState(GoalState.OVERDUE);
                                    goalDao.createGoal(goal);
                                })
                );
    }
}
