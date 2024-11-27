package com.finance.app.goal.boundary.response;

import com.finance.app.goal.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record BoardGoalResponse(
        int boardGoalId,
        BigDecimal boardBalance,
        CurrencyCode currency
) {}
