package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.service.process.TransactionDepositProcess;
import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/saving/{savingId}/transaction/deposit")
public class TransactionDepositController {
    private final UserAccountService userAccountService;
    private final TransactionDepositProcess transactionDepositProcess;

    TransactionDepositController(TransactionDepositProcess transactionDepositProcess, UserAccountService userAccountService) {
        this.transactionDepositProcess = transactionDepositProcess;
        this.userAccountService = userAccountService;
    }

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
