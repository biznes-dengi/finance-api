package com.finance.app.apiprovider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonProvider {

    @Value("${api-provider.common}")
    public String URL;

    void fetchCurrencyRate() {

    }
}
