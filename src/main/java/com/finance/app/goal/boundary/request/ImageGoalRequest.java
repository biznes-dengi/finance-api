package com.finance.app.goal.boundary.request;

import com.finance.app.goal.domain.enums.ImageType;

public record ImageGoalRequest(
        String value,
        ImageType imageType
) { }
