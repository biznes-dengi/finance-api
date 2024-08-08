package com.maksyank.finance.saving.boundary.response;

import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.domain.enums.RiskProfileType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingResponse(
        int id,
        String title,
        SavingState state,
        String description,
        BigDecimal amount,
        BigDecimal targetAmount,
        LocalDate deadline,
        RiskProfileType riskProfile,
        String image
) { }
