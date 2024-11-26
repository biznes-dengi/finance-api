package com.finance.app.domain.businessrules;

import com.finance.app.domain.enums.GoalState;

import java.math.BigDecimal;

public record InitRulesGoal(
        GoalState state,
        BigDecimal balance
) { }
