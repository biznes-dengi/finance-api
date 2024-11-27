package com.finance.app.auth.boundary.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    int id;
    String email;
    String nickname;
    LocalDateTime createdOn;
    LocalDateTime lastLogin;
}
