package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.service.process.TransactionDepositProcess;
import com.maksyank.finance.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saving/{savingId}/transaction/deposit")
public class TransactionDepositController {

    private final UserAccountService userAccountService;
    private final TransactionDepositProcess transactionDepositProcess;

    @GetMapping("/month")
    public BigDecimal getDepositAmountByMonth(@PathVariable("savingId") final int savingId,
                                              @RequestParam("month") final int month,
                                              @RequestParam("year") final int year,
                                              @RequestParam("userId") final int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(savingId, year, month, userId));
    }

    private void checkIfUserExists(int userId) throws NotFoundException {
        if (this.userAccountService.checkIfNotExists(userId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(userId));
        }
    }
}
