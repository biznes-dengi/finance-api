package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.RiskProfileType;
import com.maksyank.finance.saving.domain.enums.SavingState;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingResponse(
        int id,
        String name,
        SavingState state,
        String description,
        BalanceResponse balance,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image
) { }
