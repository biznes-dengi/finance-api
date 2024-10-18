package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.SavingState;

import java.math.BigDecimal;

public record StateOfSavingResponse(
        BigDecimal balance,
        SavingState state
) { }
