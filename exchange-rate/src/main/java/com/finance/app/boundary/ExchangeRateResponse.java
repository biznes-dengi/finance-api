package com.finance.app.boundary;

import java.math.BigDecimal;

public record ExchangeRateResponse(
        BigDecimal fromTo,
        BigDecimal toFrom
) {}
