package com.finance.app.goal.domain.dto;

import com.finance.app.goal.domain.enums.CurrencyCode;
import com.finance.app.goal.domain.enums.RiskProfileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalDto(
        @Size(min = 3, max = 25)
        @NotBlank
        String title,
        @NotNull(message = "Currency should not be null.")
        CurrencyCode currency,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        ImageGoalDto image
) { }
