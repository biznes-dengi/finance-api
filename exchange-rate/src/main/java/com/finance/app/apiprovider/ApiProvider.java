package com.finance.app.apiprovider;

import com.finance.app.boundary.ExchangeRateResponse;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RegionCode;

import java.time.LocalDate;

public abstract class ApiProvider {
    public final RegionCode regionCode;

    protected ApiProvider(RegionCode regionCode) {
        this.regionCode = regionCode;
    }

    protected abstract ExchangeRateResponse fetchExchangeRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo);
}
