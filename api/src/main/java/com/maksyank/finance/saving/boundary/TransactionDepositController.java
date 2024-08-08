package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.service.process.TransactionDepositProcess;
import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/saving/{savingId}/transaction/deposit")
public class TransactionDepositController {
    private UserAccountService userAccountService;
    private TransactionDepositProcess transactionDepositProcess;

    TransactionDepositController(TransactionDepositProcess transactionDepositProcess, UserAccountService userAccountService) {
        this.transactionDepositProcess = transactionDepositProcess;
        this.userAccountService = userAccountService;
    }

    // TODO add validation for month / year
    // TODO month = 1 to 12
    // TODO year from 1970 to current
    @GetMapping("/month")
    public BigDecimal getDepositAmountByMonth(
            @PathVariable("savingId") int savingId,
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.transactionDepositProcess.processGetFundAmountByMonth(savingId, year, month, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    private void checkIfUserExists(int userId) {
        if (!this.userAccountService.checkIfExists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity 'User' not found by attribute 'id' = " + userId);
        }
    }
}
