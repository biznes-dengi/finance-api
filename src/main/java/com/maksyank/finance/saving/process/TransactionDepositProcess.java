package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.boundary.request.DepositAmountRequest;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.enums.TransactionType;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.validation.service.TransactionDepositValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDepositProcess {
    private final TransactionDepositValidationService validator;
    private final SavingDao savingDao;

    public BigDecimal processGetFundAmountByMonth(final DepositAmountRequest request) throws NotFoundException, ValidationException {
        final var savingForCalculateAmount = savingDao.fetchSavingById(request.savingId(), request.boardSavingId());

        final var resultOfValidation = validator.validate(request);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var foundDepositsByMonth = this.findDepositTransactionsByMonth(savingForCalculateAmount, request.year(), request.month());
        if (foundDepositsByMonth.isEmpty()) {
            throw new NotFoundException("Entities 'Deposit' were not found in " + request.year() + "/" + request.month());
        }
        return this.computeSumAmount(foundDepositsByMonth);
    }

    // TODO critical point. For big data troubles with time of response (maybe move logic to SQL query)
    // TODO maybe split logic into methods by filters. It relates from if there's a need for it
    private List<Transaction> findDepositTransactionsByMonth(Saving source, int year, int month) {
        return source.getTransactions().stream()
                .filter(deposit -> deposit.getType() == TransactionType.DEPOSIT)
                .filter(deposit -> deposit.getTransactionTimestamp().getYear() == year)
                .filter(deposit -> deposit.getTransactionTimestamp().getMonthValue() == month)
                .toList();
    }

    // TODO maybe combine with findFundDepositsByMonth() for best performance
    private BigDecimal computeSumAmount(List<Transaction> source) {
        return source.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
