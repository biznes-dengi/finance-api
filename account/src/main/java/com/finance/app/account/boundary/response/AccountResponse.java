package com.finance.app.account.boundary.response;

import java.time.LocalDateTime;

public record AccountResponse(
        int id,
        String email,
        String username,
        LocalDateTime createdOn,
        LocalDateTime lastLogin
) { }
