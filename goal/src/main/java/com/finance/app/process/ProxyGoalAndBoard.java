package com.finance.app.process;

import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.enums.TransactionType;
import com.finance.app.exception.ParentException;
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
