package com.finance.app.domain.dto;

import com.finance.app.domain.enums.ImageType;

public record ImageSavingDto(
        String value,
        ImageType imageType
) { }
