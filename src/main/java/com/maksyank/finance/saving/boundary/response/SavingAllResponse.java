package com.maksyank.finance.saving.boundary.response;

import java.util.List;

public record SavingAllResponse(
        List<SavingViewResponse> savings,
        boolean hasNext
) {}
