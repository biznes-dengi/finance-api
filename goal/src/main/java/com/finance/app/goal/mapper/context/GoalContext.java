package com.finance.app.goal.mapper.context;

import com.finance.app.goal.domain.Goal;

public class GoalContext {

    public Goal goal;

    public GoalContext(Goal goal) {
        this.goal = goal;
    }
}
