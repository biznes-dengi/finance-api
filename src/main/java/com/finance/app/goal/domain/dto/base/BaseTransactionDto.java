package com.finance.app.goal.domain.dto.base;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTransactionDto {
    @Size(max = 100, message = "The 'description' field must contain less than [100] symbols.")
    private String description;
}
