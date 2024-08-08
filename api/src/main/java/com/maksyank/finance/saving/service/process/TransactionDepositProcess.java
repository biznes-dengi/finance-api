package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.TransactionType;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionDepositProcess {
    private SavingPersistence savingPersistence;

    @Autowired
    TransactionDepositProcess(SavingPersistence savingPersistence) {
        this.savingPersistence = savingPersistence;
    }

    public BigDecimal processGetFundAmountByMonth(int savingId, int year, int month, int userId) throws NotFoundException {
        final var savingForCalculateAmount = this.savingPersistence.findByIdAndUserId(savingId, userId);

        final var foundDepositsByMonth = this.findDepositTransactionsByMonth(savingForCalculateAmount, year, month);
        if (foundDepositsByMonth.size() == 0) {
            throw new NotFoundException("Entities 'Deposit' were not found in " + year + "/" + month);
        }
        return this.computeSumAmount(foundDepositsByMonth);
    }

    // TODO critical point. For big data troubles with time of response (maybe move logic to SQL query)
    // TODO change filter fund to enum
    // TODO maybe split logic into methods by filters. It relates from if there's a need for it
    private List<Transaction> findDepositTransactionsByMonth(Saving source, int year, int month) {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getType() == TransactionType.DEPOSIT)
                .filter(deposit -> deposit.getDealDate().getYear() == year)
                .filter(deposit -> deposit.getDealDate().getMonthValue() == month)
                .toList();
    }

    // TODO maybe combine with findFundDepositsByMonth() for best performance
    private BigDecimal computeSumAmount(List<Transaction> source) {
        return source.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
