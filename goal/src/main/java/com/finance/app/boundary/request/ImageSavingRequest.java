package com.finance.app.boundary.request;

import com.finance.app.domain.enums.ImageType;

public record ImageSavingRequest(
        String value,
        ImageType imageType
) { }
