package com.finance.app.boundary.request;

import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RiskProfileType;

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
