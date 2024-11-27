package com.finance.app.goal.boundary.response;

import java.util.List;

public record GoalAllResponse(
        List<GoalViewResponse> items,
        boolean hasNext
) {}
