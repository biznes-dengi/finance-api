package com.finance.app.process;

import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.Goal;
import com.finance.app.domain.dto.TransactionTransferDto;
import com.finance.app.domain.enums.TransactionType;
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

    public Goal proxyToUpdateGoalBalance(TransactionType type, BigDecimal amount, int goalId, int boardGoalId)
            throws ParentException {
        return goalProcess.updateGoalBalance(type, amount, goalId, boardGoalId);
    }

    public BoardGoal proxyToUpdateBoardBalance(TransactionType type, BigDecimal amount, int boardGoalId)
            throws ParentException {
        return boardGoalProcess.updateBoardBalance(type, amount, boardGoalId);
    }

    public List<Goal> proxyToUpdateGoalBalancesByTransferTransaction(TransactionTransferDto transferDto, int boardGoalId)
            throws ParentException {
        return goalProcess.updateGoalBalancesByTransferTransaction(transferDto, boardGoalId);
    }
}
