package com.finance.app.boundary.response;

import java.util.List;

public record GoalAllResponse(
        List<GoalViewResponse> items,
        boolean hasNext
) {}
