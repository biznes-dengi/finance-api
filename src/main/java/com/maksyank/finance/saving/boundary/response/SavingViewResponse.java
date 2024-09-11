package com.maksyank.finance.saving.boundary.response;

import java.math.BigDecimal;

public record SavingViewResponse(
        int id,
        String name,
        BigDecimal balance,
        BigDecimal targetAmount,
        String image
) { }
