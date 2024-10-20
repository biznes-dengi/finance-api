package com.maksyank.finance.saving.boundary.response;

import java.util.List;

public record TransactionAllResponse(
        List<TransactionViewResponse> items,
        boolean hasNext
) {}
