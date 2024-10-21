package com.finance.app.boundary.response;

import com.finance.app.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record GoalViewResponse(
        int id,
        String name,
        BalanceResponse balance,
        CurrencyCode currency,
        BigDecimal targetAmount,
        String image
) { }
