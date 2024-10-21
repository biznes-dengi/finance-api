package com.finance.app.domain.dto;

import com.finance.app.domain.enums.ImageType;

public record ImageGoalDto(
        String value,
        ImageType imageType
) { }
