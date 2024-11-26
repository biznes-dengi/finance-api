package com.finance.app.auth.boundary.response;

public record ValidationResponse(String username, boolean isValid) {
}
