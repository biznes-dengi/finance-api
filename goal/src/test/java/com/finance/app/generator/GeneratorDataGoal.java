package com.finance.app.generator;

import com.finance.app.domain.Goal;
import com.finance.app.domain.Transaction;
import com.finance.app.domain.dto.ImageGoalDto;
import com.finance.app.domain.dto.GoalDto;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.ImageType;
import com.finance.app.domain.enums.RiskProfileType;
import com.finance.app.domain.enums.GoalState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneratorDataGoal {

    public static Goal getTestData_testUpdateBalance_01() {
        Goal goal = new Goal();
        List<Transaction> transactions = new ArrayList<>();
        goal.setGoalBalance(BigDecimal.ZERO);
        goal.setState(GoalState.ACTIVE);
        goal.setTargetAmount(BigDecimal.valueOf(900));
        goal.setTransactions(transactions);
        return goal;
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_01() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                null,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_02() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.ZERO,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_03() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(-120.03),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_04() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(120),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_05() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(120.1),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_06() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(120.182),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_AllValidData() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(120.18),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testTargetAmountValidationStep_08() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(120.18),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testDeadlineValidationStep_01() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                null,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testDeadlineValidationStep_02() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                null,
                null,
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testDeadlineValidationStep_03() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2020, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testDeadlineValidationStep_AllValidData() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("", ImageType.JPEG)
        );
    }

    public static GoalDto getTestData_testImageTypeValidationStep_01() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("sfkfdskl", null)
        );
    }

    public static GoalDto getTestData_testImageTypeValidationStep_02() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto(null, ImageType.JPG)
        );
    }

    public static GoalDto getTestData_testImageTypeValidationStep_03() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto(null, null)
        );
    }

    public static GoalDto getTestData_testImageTypeValidationStep_04() {
        return new GoalDto(
                "test",
                CurrencyCode.EUR,
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                new ImageGoalDto("fdsfdsfds", ImageType.JPEG)
        );
    }

    public static Goal getTestData_testUpdateState_01() {
        Goal goal = new Goal();
        goal.setGoalBalance(BigDecimal.valueOf(120.2));
        goal.setState(GoalState.OVERDUE);
        return goal;
    }

    public static Goal getTestData_testUpdateState_02() {
        Goal goal = new Goal();
        goal.setGoalBalance(BigDecimal.valueOf(120.2));
        goal.setTargetAmount(BigDecimal.valueOf(150));
        goal.setState(GoalState.ACTIVE);
        return goal;
    }

    public static Goal getTestData_testUpdateState_03() {
        Goal goal = new Goal();
        goal.setGoalBalance(BigDecimal.valueOf(160.9));
        goal.setTargetAmount(BigDecimal.valueOf(150));
        goal.setState(GoalState.ACHIEVED);
        return goal;
    }

    public static List<Goal> getTestData_testScheduledCheckGoalsIfOverdue_01() {
        var goal = new Goal();
        goal.setState(GoalState.ACTIVE);
        goal.setDeadline(LocalDate.of(2024, 1, 23));
        return List.of(goal);
    }

    public static List<Goal> getTestData_testScheduledCheckGoalsIfOverdue_02() {
        var goal = new Goal();
        goal.setState(GoalState.ACTIVE);
        goal.setDeadline(LocalDate.of(2100, 1, 23));
        return List.of(goal);
    }

    public static Goal getTestData_testProcessSave_01() {
        final Goal response = new Goal();
        response.setId(1);
        response.setTitle("test_01");
        return response;
    }

    public static List<Goal> getTestData_testProcessSaveTransactionTransfer_01() {
        final Goal goal1 = new Goal();
        goal1.setId(1);
        goal1.setTitle("test_01");
        final Goal goal2 = new Goal();
        goal2.setId(1);
        goal2.setTitle("test_01");
        return List.of(goal1, goal2);
    }
}
