package com.finance.app.boundary;

import com.finance.app.domain.enums.AccountGender;
import com.finance.app.domain.enums.AppRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountRequest(
        AppRole role,
        String email,
        String pass,
        String firstName,
        String lastName,
        AccountGender gender,
        LocalDate dateOfBirth,
        String phoneNumber,
        LocalDateTime createdOn
) { }
