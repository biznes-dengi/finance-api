package com.finance.app.boundary;

import com.finance.app.apiprovider.CommonClient;
import com.finance.app.apiprovider.russia.RussiaNationalBankClient;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RegionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final RussiaNationalBankClient russiaNationalBankClient;
    private final CommonClient commonClient;

    @GetMapping()
    public ExchangeRateResponse getExchangeRate(
            @RequestParam("from") CurrencyCode from,
            @RequestParam("to") CurrencyCode to,
            @RequestParam("regionCode") RegionCode regionCode,
            @RequestParam("date") LocalDate date
    ) {
        return commonClient.fetchCurrencyRate(date, from, to);
    }
}

