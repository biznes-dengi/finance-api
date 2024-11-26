package com.finance.app.exchangerate.boundary;

import java.math.BigDecimal;

public record ExchangeRateResponse(
        BigDecimal fromTo,
        BigDecimal toFrom
) {}
