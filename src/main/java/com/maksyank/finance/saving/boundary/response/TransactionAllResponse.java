package com.maksyank.finance.saving.boundary.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TransactionAllResponse(
        @JsonProperty("transactions")
        List<TransactionViewResponse> transactionViewResponseList,
        boolean hasNext
) {}
