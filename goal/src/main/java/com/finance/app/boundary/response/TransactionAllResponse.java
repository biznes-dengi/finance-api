package com.finance.app.boundary.response;

import java.util.List;

public record TransactionAllResponse(
        List<TransactionViewResponse> items,
        boolean hasNext
) {}
