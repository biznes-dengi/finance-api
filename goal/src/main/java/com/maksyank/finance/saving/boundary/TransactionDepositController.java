package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.process.TransactionDepositProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("board-savings/{boardSavingId}/savings/{savingId}/transactions/deposits")
public class TransactionDepositController {
    private final TransactionDepositProcess transactionDepositProcess;

    @GetMapping("/month")
    public BigDecimal getDepositAmountByMonth(@PathVariable("savingId") final int savingId,
                                              @RequestParam("month") final int month,
                                              @RequestParam("year") final int year,
                                              @PathVariable("boardSavingId") final int boardSavingId) throws ParentException {
        return this.transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(savingId, year, month, boardSavingId));
    }
}
