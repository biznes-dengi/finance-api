package com.finance.app.apiprovider.common;

import com.finance.app.apiprovider.ApiProvider;
import com.finance.app.boundary.ExchangeRateResponse;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RegionCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * That provider is used if country of user is not supported or with another providers any troubles.
 */
@Component
public class CommonProviderClient extends ApiProvider {

    private final RestClient restClient;

    private final String uriVariables = "{date}/v1/currencies/{currencyFrom}.json";

    public CommonProviderClient(RestClient restClient) {
        super(RegionCode.NONE);
        this.restClient = restClient;
    }

    @Override
    public ExchangeRateResponse fetchExchangeRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        final var preparedCurrencyFrom = prepareCurrencyToRequest(currencyFrom);
        final var nativeResponseFromProvider = requestToProvider(byDate, preparedCurrencyFrom);
        return mapToExchangeRateResponse(nativeResponseFromProvider, currencyFrom, currencyTo);
    }

    private CommonProviderNativeResponse requestToProvider(LocalDate byDate, String currencyFrom) {
        return restClient
                .get()
                .uri(uriVariables, byDate.toString(), currencyFrom)
                .retrieve()
                .toEntity(CommonProviderNativeResponse.class)
                .getBody();
    }

    private ExchangeRateResponse mapToExchangeRateResponse(CommonProviderNativeResponse source, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        final var allCurrencyRate = (LinkedHashMap<String, Double>) source.getData().get(currencyFrom.name().toLowerCase());
        final var currencyToRate = BigDecimal.valueOf(allCurrencyRate.get(currencyTo.name().toLowerCase()));
        final var currencyFromRate = BigDecimal.ONE.divide(currencyToRate, MathContext.DECIMAL64);

        return new ExchangeRateResponse(roundForResponse(currencyToRate), roundForResponse(currencyFromRate));
    }

    private String prepareCurrencyToRequest(CurrencyCode currencyCode) {
        return currencyCode.toString().toLowerCase();
    }

    private BigDecimal roundForResponse(BigDecimal value) {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
