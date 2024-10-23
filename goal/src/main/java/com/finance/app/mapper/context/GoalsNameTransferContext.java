package com.finance.app.mapper.context;

public class GoalsNameTransferContext {

    public String toGoalName;
    public String fromGoalName;

    public GoalsNameTransferContext(String toGoalName, String fromGoalName) {
        this.toGoalName = toGoalName;
        this.fromGoalName = fromGoalName;
    }
}
