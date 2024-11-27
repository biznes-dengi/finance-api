package com.finance.app.goal.boundary.response;

import com.finance.app.goal.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record GoalViewResponse(
        int id,
        String name,
        BalanceResponse balance,
        CurrencyCode currency,
        BigDecimal targetAmount,
        String image
) { }
