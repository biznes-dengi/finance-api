package com.finance.app.auth.boundary.request;

import com.finance.app.account.domain.enums.AccountGender;
import com.finance.app.account.domain.enums.AppRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterRequest(
        AppRole role,
        String email,
        String pass,
        String nickname,
        AccountGender gender,
        LocalDate dateOfBirth,
        String phoneNumber,
        LocalDateTime createdOn
) { }
