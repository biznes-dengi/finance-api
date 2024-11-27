package com.finance.app.exchangerate.apiprovider;

import com.finance.app.exchangerate.boundary.ExchangeRateResponse;
import com.finance.app.exchangerate.enums.CurrencyCode;
import com.finance.app.exchangerate.enums.RegionCode;

import java.time.LocalDate;

public abstract class ApiProvider {
    public final RegionCode regionCode;

    protected ApiProvider(RegionCode regionCode) {
        this.regionCode = regionCode;
    }

    protected abstract ExchangeRateResponse fetchExchangeRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo);
}
