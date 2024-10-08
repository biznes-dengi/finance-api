package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public record BoardSavingResponse(
        int boardSavingId,
        BigDecimal boardBalance,
        CurrencyCode currency
) {}
