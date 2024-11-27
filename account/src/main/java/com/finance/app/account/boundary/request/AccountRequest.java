package com.finance.app.account.boundary.request;

public record AccountRequest(
        String email,
        String pass
) { }
