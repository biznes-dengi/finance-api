package com.maksyank.finance.saving.domain.dto;

import com.maksyank.finance.saving.domain.enums.ImageType;

public record ImageSavingDto(
        String value,
        ImageType imageType
) { }
