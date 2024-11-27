package com.finance.app.goal.process;

import com.finance.app.goal.domain.BoardGoal;
import com.finance.app.goal.domain.enums.TransactionType;
import com.finance.app.goal.exception.ParentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProxyGoalAndBoard {
    private final BoardGoalProcess boardGoalProcess;

    public BoardGoal proxyToUpdateBoardBalance(TransactionType type, BigDecimal amount, int boardGoalId)
            throws ParentException {
        return boardGoalProcess.updateBoardBalance(type, amount, boardGoalId);
    }
}
