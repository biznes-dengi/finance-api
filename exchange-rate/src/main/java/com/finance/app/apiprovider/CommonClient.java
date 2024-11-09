package com.finance.app.apiprovider;

import com.finance.app.boundary.ExchangeRateResponse;
import com.finance.app.domain.enums.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
public class CommonClient {

    private final RestClient restClient;

    private final String uriVariables = "{date}/v1/currencies/{currencyFrom}.json";

    public ExchangeRateResponse fetchCurrencyRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        final var preparedCurrencyFrom = prepareCurrencyToRequest(currencyFrom);
        final var nativeResponseFromProvider = requestToProvider(byDate, preparedCurrencyFrom);
        return mapToExchangeRateResponse(nativeResponseFromProvider, currencyFrom, currencyTo);
    }

    private CommonResponse requestToProvider(LocalDate byDate, String currencyFrom) {
        return restClient
                .get()
                .uri(uriVariables, byDate.toString(), currencyFrom)
                .retrieve()
                .toEntity(CommonResponse.class)
                .getBody();
    }

    private ExchangeRateResponse mapToExchangeRateResponse(CommonResponse source, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
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
