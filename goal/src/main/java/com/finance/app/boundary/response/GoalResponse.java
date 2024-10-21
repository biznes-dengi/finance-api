package com.finance.app.boundary.response;

import com.finance.app.domain.enums.RiskProfileType;
import com.finance.app.domain.enums.GoalState;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalResponse(
        int id,
        String name,
        GoalState state,
        BalanceResponse balance,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image
) { }
