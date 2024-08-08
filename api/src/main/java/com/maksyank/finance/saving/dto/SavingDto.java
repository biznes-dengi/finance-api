package com.maksyank.finance.saving.dto;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.domain.enums.ImageType;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingDto(
        @Size(min = 3, max = 25)
        @NotBlank
        String title,
        @NotNull
        CurrencyCode currency,
        @Size(max = 100)
        String description,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image,
        ImageType imageType
) { }
