package com.maksyank.finance.saving.domain.dto;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.domain.enums.ImageType;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingDto(
        @Size(min = 3, max = 25)
        @NotBlank
        String title,
        @NotNull
        CurrencyCode currency,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        ImageSavingDto image
) { }
