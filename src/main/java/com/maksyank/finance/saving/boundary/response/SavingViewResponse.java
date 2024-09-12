package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record SavingViewResponse(
        int id,
        String name,
        BigDecimal balance,
        CurrencyCode currency,
        BigDecimal targetAmount,
        String image
) { }
