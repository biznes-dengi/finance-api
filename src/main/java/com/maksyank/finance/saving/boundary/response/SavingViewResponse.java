package com.maksyank.finance.saving.boundary.response;

import java.math.BigDecimal;

public record SavingViewResponse(
        int id,
        String title,
        BigDecimal amount,
        BigDecimal targetAmount,
        String image
) { }
