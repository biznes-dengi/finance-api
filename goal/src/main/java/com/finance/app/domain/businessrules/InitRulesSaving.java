package com.finance.app.domain.businessrules;

import com.finance.app.domain.enums.SavingState;

import java.math.BigDecimal;

public record InitRulesSaving(
        SavingState state,
        BigDecimal balance
) { }
