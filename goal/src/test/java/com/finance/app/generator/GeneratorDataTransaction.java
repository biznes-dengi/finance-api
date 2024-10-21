package com.finance.app.generator;

import com.finance.app.boundary.request.TransactionRequest;
import com.finance.app.boundary.request.TransactionTransferRequest;
import com.finance.app.domain.Goal;
import com.finance.app.domain.Transaction;
import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GeneratorDataTransaction {

    public static Goal getTestData_testProcessGetDepositAmountByMonth_01() {
        Goal goal = new Goal();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 10, 18, 20), new BigDecimal("102.88"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 2, 13, 21, 44), new BigDecimal("1402.02"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 2, 28, 23, 59), new BigDecimal("505.02"), goal));
        transactions.add(new Transaction(
                TransactionType.DEPOSIT, "test",
                LocalDateTime.of(2023, 3, 1, 0, 0),
                new BigDecimal("932.02"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.DEPOSIT, "test",
                LocalDateTime.of(2023, 3, 10, 10, 0),
                new BigDecimal("932.02"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.WITHDRAW, "test",
                LocalDateTime.of(2023, 3, 11, 9, 0),
                new BigDecimal("-150.22"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.DEPOSIT, "test",
                LocalDateTime.of(2023, 3, 12, 6, 0),
                new BigDecimal("222.23"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.WITHDRAW, "test",
                LocalDateTime.of(2023, 3, 13, 12, 0),
                new BigDecimal("-99.12"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.DEPOSIT, "test",
                LocalDateTime.of(2023, 3, 14, 15, 0),
                new BigDecimal("428.93"),
                goal)
        );
        transactions.add(new Transaction(
                TransactionType.WITHDRAW, "test",
                LocalDateTime.of(2023, 3, 22, 18, 0),
                new BigDecimal("-104.54"), goal)
        );
        transactions.add(new Transaction(
                TransactionType.DEPOSIT, "test",
                LocalDateTime.of(2023, 3, 31, 23, 59),
                new BigDecimal("932.02"), goal)
        );
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 4, 1, 0, 0), new BigDecimal("809.32"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 4, 15, 11, 20), new BigDecimal("1222.82"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 5, 16, 12, 25), new BigDecimal("762.32"), goal));
        goal.setTransactions(transactions);
        return goal;
    }

    public static Goal getTestData_testProcessGetDepositAmountByMonth_02() {
        Goal goal = new Goal();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 10, 10, 0), new BigDecimal("932.02"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 11, 9, 0), new BigDecimal("-150.22"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 12, 6, 0), new BigDecimal("222.23"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 13, 12, 0), new BigDecimal("-99.12"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 14, 15, 0), new BigDecimal("428.93"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 5, 18, 0), new BigDecimal("-104.54"), goal));
        goal.setTransactions(transactions);
        return goal;
    }

    public static Goal getTestData_testFindTransactions_01() {
        Goal goal = new Goal();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(101, TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 10, 10, 0), new BigDecimal("932.02"), goal));
        transactions.add(new Transaction(102, TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 11, 9, 0), new BigDecimal("-150.22"), goal));
        transactions.add(new Transaction(103, TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 12, 6, 0), new BigDecimal("222.23"), goal));
        transactions.add(new Transaction(104, TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 13, 12, 0), new BigDecimal("-99.12"), goal));
        transactions.add(new Transaction(105, TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 14, 15, 0), new BigDecimal("428.93"), goal));
        transactions.add(new Transaction(106, TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 5, 18, 0), new BigDecimal("-104.54"), goal));
        goal.setTransactions(transactions);
        return goal;
    }

    public static Goal getTestData_testFindTransactions_02() {
        Goal goal = new Goal();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 10, 10, 0), new BigDecimal("932.02"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 11, 9, 0), new BigDecimal("-150.22"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 12, 6, 0), new BigDecimal("222.23"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 13, 12, 0), new BigDecimal("-99.12"), goal));
        transactions.add(new Transaction(TransactionType.DEPOSIT, "test", LocalDateTime.of(2023, 1, 14, 15, 0), new BigDecimal("428.93"), goal));
        transactions.add(new Transaction(TransactionType.WITHDRAW, "test", LocalDateTime.of(2023, 1, 5, 18, 0), new BigDecimal("-104.54"), goal));
        goal.setTransactions(transactions);
        return goal;
    }

    public static TransactionDto getTestData_testAmountValidationStep_04() {
        return new TransactionDto(
                TransactionType.DEPOSIT,
                "test",
                null,
                null,
                null,
                BigDecimal.valueOf(-98.98)
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_05() {
        return new TransactionDto(
                TransactionType.DEPOSIT,
                "test",
                null,
                null,
                null,
                BigDecimal.ZERO
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_06() {
        return new TransactionDto(
                TransactionType.DEPOSIT,
                "test",
                null,
                null,
                null,
                BigDecimal.valueOf(98.20)
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_07() {
        return new TransactionDto(
                TransactionType.WITHDRAW,
                "test",
                null,
                null,
                null,
                BigDecimal.valueOf(-98.10)
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_08() {
        return new TransactionDto(
                TransactionType.WITHDRAW,
                "test",
                null,
                null,
                null,
                BigDecimal.ZERO
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_09() {
        return new TransactionDto(
                TransactionType.WITHDRAW,
                "test",
                null,
                null,
                null,
                BigDecimal.valueOf(98.13)
        );
    }

    public static TransactionDto getTestData_testTypeValidation_01() {
        return new TransactionDto(
                TransactionType.TRANSFER,
                "bla",
                LocalDateTime.now(),
                1,
                2,
                BigDecimal.valueOf(1032.10)
        );
    }

    public static TransactionDto getTestData_testTypeValidation_02() {
        return new TransactionDto(
                TransactionType.TRANSFER,
                "bla",
                LocalDateTime.now(),
                null,
                null,
                BigDecimal.valueOf(1032.10)
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_10() {
        return new TransactionDto(
                TransactionType.TRANSFER,
                "bla",
                LocalDateTime.now(),
                1,
                2,
                BigDecimal.ZERO
        );
    }

    public static TransactionDto getTestData_testAmountValidationStep_11() {
        return new TransactionDto(
                TransactionType.TRANSFER,
                "bla",
                LocalDateTime.now(),
                1,
                2,
                BigDecimal.valueOf(-10232)
        );
    }

    public static TransactionDto getTestData_testTypeValidation_05() {
        return new TransactionDto(
                TransactionType.DEPOSIT,
                "bla",
                LocalDateTime.now(),
                null,
                null,
                BigDecimal.valueOf(3220)
        );
    }

    public static TransactionDto getTestData_testTypeValidation_06() {
        return new TransactionDto(
                TransactionType.DEPOSIT,
                "bla",
                LocalDateTime.now(),
                2,
                3,
                BigDecimal.valueOf(1002)
        );
    }

    public static TransactionRequest getTestData_testProcessSave_01_payload() {
        return new TransactionRequest(
                TransactionType.DEPOSIT,
                "bla",
                LocalDateTime.now(),
                BigDecimal.valueOf(1032)
        );
    }

    public static TransactionTransferRequest getTestData_testProcessSaveTransactionTransfer_01() {
        return new TransactionTransferRequest(
                "bla",
                LocalDateTime.now(),
                1,
                2,
                BigDecimal.valueOf(1032)
        );
    }
}
