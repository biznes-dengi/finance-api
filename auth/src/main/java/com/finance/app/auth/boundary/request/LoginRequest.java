package com.finance.app.auth.boundary.request;

import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @param login Can be an email or a email
 * @param password Password of user
 */
public record LoginRequest(
        @NotEmpty String login,
        @NotEmpty String password) {
}
