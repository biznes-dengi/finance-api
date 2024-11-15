package com.finance.app.boundary.response;

import com.finance.app.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal amount,
        CurrencyCode currency
) { }