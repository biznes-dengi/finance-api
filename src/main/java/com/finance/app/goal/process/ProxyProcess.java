package com.finance.app.goal.process;

import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.dto.TransactionTransferDto;
import com.finance.app.goal.domain.enums.TransactionType;
import com.finance.app.goal.exception.ParentException;
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

    public Goal proxyToUpdateGoalBalance(TransactionType type, BigDecimal amount, int goalId, int boardGoalId)
            throws ParentException {
        return goalProcess.updateGoalBalance(type, amount, goalId, boardGoalId);
    }

    public List<Goal> proxyToUpdateGoalBalancesByTransferTransaction(TransactionTransferDto transferDto, int boardGoalId)
            throws ParentException {
        return goalProcess.updateGoalBalancesByTransferTransaction(transferDto, boardGoalId);
    }
}
