package com.finance.app.boundary.request;

import jakarta.validation.constraints.NotEmpty;

public record ValidationRequest(
        @NotEmpty String token) {
}
