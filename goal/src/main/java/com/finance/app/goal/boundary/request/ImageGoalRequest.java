package com.finance.app.goal.boundary.request;

import com.finance.app.domain.enums.ImageType;

public record ImageGoalRequest(
        String value,
        ImageType imageType
) { }