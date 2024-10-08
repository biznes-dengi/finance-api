package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal amount,
        CurrencyCode currency
) { }
