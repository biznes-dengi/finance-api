package com.finance.app.boundary;

import com.finance.app.apiprovider.russia.RussiaNationalBankClient;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final RussiaNationalBankClient russiaNationalBankClient;

    @GetMapping()
    public SOAPMessage getExchangeRate(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("regionCode") String regionCode,
            @RequestParam("date") LocalDate date
    ) throws SOAPException, JAXBException, IOException {
        return russiaNationalBankClient.fetchCurrencyRate(LocalDate.of(2024, 9, 9));
    }
}

