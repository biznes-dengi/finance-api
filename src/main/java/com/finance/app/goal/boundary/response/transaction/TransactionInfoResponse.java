package com.finance.app.goal.boundary.response.transaction;

public record TransactionInfoResponse (
        boolean hasNext,
        int pageNumber,
        int pageSize
) { }
