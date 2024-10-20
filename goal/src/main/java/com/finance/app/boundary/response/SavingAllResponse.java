package com.finance.app.boundary.response;

import java.util.List;

public record SavingAllResponse(
        List<SavingViewResponse> items,
        boolean hasNext
) {}
