package com.finance.app.goal.boundary.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.app.goal.domain.enums.GoalState;
import com.finance.app.goal.domain.enums.RiskProfileType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalResponse(
        int id,
        String name,
        GoalState state,
        BalanceResponse balance,
        BigDecimal targetAmount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image
) { }
