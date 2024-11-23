package com.finance.app.boundary.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty String username,
        @NotEmpty String password) {
}
