package com.finance.app.goal.domain.dto;

import com.finance.app.goal.domain.enums.ImageType;

public record ImageGoalDto(
        String value,
        ImageType imageType
) { }
