package com.finance.app.auth.boundary.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty String email,
        @NotEmpty String password) {
}
