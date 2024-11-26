package com.finance.app.auth.boundary.request;

import jakarta.validation.constraints.NotEmpty;

public record ValidationRequest(
        @NotEmpty String token) {
}
