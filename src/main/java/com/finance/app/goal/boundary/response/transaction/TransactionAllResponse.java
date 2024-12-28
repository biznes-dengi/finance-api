package com.finance.app.goal.boundary.response.transaction;

import java.util.List;

public record TransactionAllResponse(
        List<TransactionResponse> items,
        TransactionInfoResponse info
) {}
