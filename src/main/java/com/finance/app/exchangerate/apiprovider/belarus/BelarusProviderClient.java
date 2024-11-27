package com.finance.app.exchangerate.apiprovider.belarus;

import com.finance.app.exchangerate.apiprovider.ApiProvider;
import com.finance.app.exchangerate.boundary.ExchangeRateResponse;
import com.finance.app.exchangerate.enums.CurrencyCode;
import com.finance.app.exchangerate.enums.RegionCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * API provider - National Bank of Belarus.
 */
@Component
public class BelarusProviderClient extends ApiProvider {

    public BelarusProviderClient() {
        super(RegionCode.BY);
    }

    @Override
    protected ExchangeRateResponse fetchExchangeRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        return null;
    }
}
