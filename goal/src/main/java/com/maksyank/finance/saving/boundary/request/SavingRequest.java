package com.maksyank.finance.saving.boundary.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingRequest(
        String name,
        CurrencyCode currency,
        String description,
        BigDecimal targetAmount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate deadline,
        RiskProfileType riskProfile,
        ImageSavingRequest image
) { }
