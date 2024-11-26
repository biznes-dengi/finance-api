package com.finance.app.goal.boundary.response;

import com.finance.app.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal amount,
        CurrencyCode currency
) { }
