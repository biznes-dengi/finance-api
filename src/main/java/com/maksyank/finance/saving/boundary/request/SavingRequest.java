package com.maksyank.finance.saving.boundary.request;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingRequest(
        String name,
        CurrencyCode currency,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        ImageSavingRequest image
) { }
