package com.finance.app.domain.dto;

import com.finance.app.domain.dto.base.BaseTransactionDto;

public class TransactionUpdateDto extends BaseTransactionDto {
        public TransactionUpdateDto(String description) {
                super(description);
        }
}
