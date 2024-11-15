package com.finance.app.boundary.request;

import com.finance.app.domain.enums.AccountGender;
import com.finance.app.domain.enums.AppRole;

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
