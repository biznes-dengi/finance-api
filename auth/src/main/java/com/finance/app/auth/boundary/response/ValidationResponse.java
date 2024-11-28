package com.finance.app.auth.boundary.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "aResponse", toBuilder = true)
public class ValidationResponse {

    private final long telegramId;
    private final String email;
    private final String firstName;
    private final String username;
    private final boolean valid;
}
