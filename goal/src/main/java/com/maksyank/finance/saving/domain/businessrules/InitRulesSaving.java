package com.maksyank.finance.saving.domain.businessrules;

import com.maksyank.finance.saving.domain.enums.SavingState;

import java.math.BigDecimal;

public record InitRulesSaving(
        SavingState state,
        BigDecimal balance
) { }
