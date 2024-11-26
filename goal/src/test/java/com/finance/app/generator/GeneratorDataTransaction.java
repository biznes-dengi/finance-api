package com.finance.app.goal.generator;

import com.finance.app.goal.boundary.request.TransactionRequest;
import com.finance.app.goal.boundary.request.TransactionTransferRequest;
import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.Transaction;
import com.finance.app.goal.domain.enums.TransactionType;

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
                BigDecimal.valueOf(1032),
                2,
                BigDecimal.valueOf(1032)
        );
    }
}
