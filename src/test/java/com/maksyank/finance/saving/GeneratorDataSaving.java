package com.maksyank.finance.saving;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.domain.enums.ImageType;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.domain.dto.SavingDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeneratorDataSaving {

    public static Saving getTestData_testUpdateBalance_01() {
        Saving saving = new Saving();
        List<Transaction> transactions = new ArrayList<>();
        saving.setBalance(BigDecimal.ZERO);
        saving.setState(SavingState.ACTIVE);
        saving.setTargetAmount(BigDecimal.valueOf(900));
        saving.setTransactions(transactions);
        return saving;
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_01() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                null,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_02() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.ZERO,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_03() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(-120.03),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_04() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(120),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_05() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(120.1),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_06() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(120.182),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_AllValidData() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(120.18),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testTargetAmountValidationStep_08() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(120.18),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testDeadlineValidationStep_01() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                null,
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testDeadlineValidationStep_02() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                null,
                null,
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testDeadlineValidationStep_03() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2020, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testDeadlineValidationStep_AllValidData() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "",
                ImageType.JPEG
        );
    }

    public static SavingDto getTestData_testImageTypeValidationStep_01() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "sfkfdskl",
                null
        );
    }

    public static SavingDto getTestData_testImageTypeValidationStep_02() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                null,
                ImageType.JPG
        );
    }

    public static SavingDto getTestData_testImageTypeValidationStep_03() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                null,
                null
        );
    }

    public static SavingDto getTestData_testImageTypeValidationStep_04() {
        return new SavingDto(
                "test",
                CurrencyCode.EUR,
                "test",
                BigDecimal.valueOf(1102.32),
                LocalDate.of(2025, 8, 18),
                RiskProfileType.MODERATE,
                "fdsfdsfds",
                ImageType.JPEG
        );
    }

    public static Saving getTestData_testUpdateState_01() {
        Saving saving = new Saving();
        saving.setBalance(BigDecimal.valueOf(120.2));
        saving.setState(SavingState.OVERDUE);
        return saving;
    }

    public static Saving getTestData_testUpdateState_02() {
        Saving saving = new Saving();
        saving.setBalance(BigDecimal.valueOf(120.2));
        saving.setTargetAmount(BigDecimal.valueOf(150));
        saving.setState(SavingState.ACTIVE);
        return saving;
    }

    public static Saving getTestData_testUpdateState_03() {
        Saving saving = new Saving();
        saving.setBalance(BigDecimal.valueOf(160.9));
        saving.setTargetAmount(BigDecimal.valueOf(150));
        saving.setState(SavingState.ACHIEVED);
        return saving;
    }

    public static List<Saving> getTestData_testScheduledCheckSavingsIfOverdue_01() {
        var saving = new Saving();
        saving.setState(SavingState.ACTIVE);
        saving.setDeadline(LocalDate.of(2024, 1, 23));
        return List.of(saving);
    }

    public static List<Saving> getTestData_testScheduledCheckSavingsIfOverdue_02() {
        var saving = new Saving();
        saving.setState(SavingState.ACTIVE);
        saving.setDeadline(LocalDate.of(2100, 1, 23));
        return List.of(saving);
    }
}
