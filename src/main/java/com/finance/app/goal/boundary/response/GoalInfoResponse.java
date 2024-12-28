package com.finance.app.goal.boundary.response;

public record GoalInfoResponse(
        boolean hasNext,
        int pageNumber,
        int pageSize
) { }
