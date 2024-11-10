package com.finance.app.apiprovider.belarus;

import com.finance.app.apiprovider.ApiProvider;
import com.finance.app.boundary.ExchangeRateResponse;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RegionCode;
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
