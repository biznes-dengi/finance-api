package com.finance.app.exchangerate.boundary;

import com.finance.app.exchangerate.apiprovider.ApiProvider;
import com.finance.app.exchangerate.apiprovider.belarus.BelarusProviderClient;
import com.finance.app.exchangerate.apiprovider.common.CommonProviderClient;
import com.finance.app.exchangerate.apiprovider.russia.RussiaProviderClient;
import com.finance.app.exchangerate.enums.CurrencyCode;
import com.finance.app.exchangerate.enums.RegionCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
    private final RussiaProviderClient russiaProviderClient;
    private final CommonProviderClient commonProviderClient;
    private final BelarusProviderClient belarusProviderClient;

    private List<ApiProvider> listApiProviders;

    public ExchangeRateController(RussiaProviderClient russiaProviderClient, CommonProviderClient commonProviderClient, BelarusProviderClient belarusProviderClient) {
        this.russiaProviderClient = russiaProviderClient;
        this.commonProviderClient = commonProviderClient;
        this.belarusProviderClient = belarusProviderClient;

        listApiProviders = List.of(russiaProviderClient, commonProviderClient, belarusProviderClient);
    }

    @GetMapping()
    public ExchangeRateResponse getExchangeRate(
            @RequestParam("from") CurrencyCode from,
            @RequestParam("to") CurrencyCode to,
            @RequestParam("regionCode") RegionCode regionCode,
            @RequestParam("date") LocalDate date
    ) {
        return commonProviderClient.fetchExchangeRate(date, from, to);
    }

    private ApiProvider foundMatchedProvider(RegionCode regionCode) {
        return listApiProviders.stream()
                .filter(provider -> provider.regionCode == regionCode)
                .findFirst()
                .orElse(null);
    }
}

