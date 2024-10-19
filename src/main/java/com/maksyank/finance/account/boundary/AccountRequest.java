package com.maksyank.finance.account.boundary;

import com.maksyank.finance.account.domain.enums.AccountGender;
import com.maksyank.finance.account.domain.enums.AppRole;

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
