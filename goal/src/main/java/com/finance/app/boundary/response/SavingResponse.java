package com.finance.app.boundary.response;

import com.finance.app.domain.enums.RiskProfileType;
import com.finance.app.domain.enums.SavingState;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingResponse(
        int id,
        String name,
        SavingState state,
        BalanceResponse balance,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image
) { }
