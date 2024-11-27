package com.finance.app.goal.domain.businessrules;

import com.finance.app.goal.domain.enums.GoalState;

import java.math.BigDecimal;

public record InitRulesGoal(
        GoalState state,
        BigDecimal balance
) { }
