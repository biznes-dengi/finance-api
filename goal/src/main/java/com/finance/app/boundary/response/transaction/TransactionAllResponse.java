package com.finance.app.boundary.response.transaction;

import java.util.List;

public record TransactionAllResponse(
        List<TransactionResponse> items,
        boolean hasNext
) {}
