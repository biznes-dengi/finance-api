package com.finance.app.process;

import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.Goal;
import com.finance.app.exception.NotFoundException;
import com.finance.app.exception.ParentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bridge between TransactionProcess and GoalProcess.
 * The bridge helps get access from one process to another.
 *
 */
@Service
@RequiredArgsConstructor
public class ProxyProcess {
    private final GoalProcess goalProcess;
    private final BoardGoalProcess boardGoalProcess;

    public Goal proxyToUpdateGoalBalance(final BigDecimal newValue, final int goalId, final int boardGoalId)
            throws ParentException {
        return goalProcess.updateGoalBalance(newValue, goalId, boardGoalId);
    }

    public BoardGoal proxyToUpdateBoardBalance(final int boardGoalId, final BigDecimal newValue)
            throws NotFoundException {
        return boardGoalProcess.updateBoardBalance(boardGoalId, newValue);
    }

    public List<Goal> proxyToUpdateGoalBalancesWhenDoTransferTransaction(int boardGoalId, int fromGoalId, int toGoalId, BigDecimal amount)
            throws ParentException {
        return goalProcess.updateGoalBalancesWhenDoTransferTransaction(boardGoalId, fromGoalId, toGoalId, amount);
    }
}
