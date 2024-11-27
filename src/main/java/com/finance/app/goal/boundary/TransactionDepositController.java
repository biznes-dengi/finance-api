package com.finance.app.goal.boundary;

import com.finance.app.goal.boundary.request.DepositAmountRequest;
import com.finance.app.exception.ParentException;
import com.finance.app.goal.process.TransactionDepositProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("board-goals/{boardGoalId}/goals/{goalId}/transactions/deposits")
public class TransactionDepositController implements GoalBoundaryMetadata {
    private final TransactionDepositProcess transactionDepositProcess;

    @GetMapping("/month")
    public BigDecimal getDepositAmountByMonth(@PathVariable("goalId") final int goalId,
                                              @RequestParam("month") final int month,
                                              @RequestParam("year") final int year,
                                              @PathVariable("boardGoalId") final int boardGoalId) throws ParentException {
        return this.transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(goalId, year, month, boardGoalId));
    }
}
