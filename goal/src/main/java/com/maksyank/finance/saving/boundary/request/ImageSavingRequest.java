package com.maksyank.finance.saving.boundary.request;

import com.maksyank.finance.saving.domain.enums.ImageType;

public record ImageSavingRequest(
        String value,
        ImageType imageType
) { }
