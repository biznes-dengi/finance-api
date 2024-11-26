package com.finance.app.exchangerate.apiprovider.russia;

import com.finance.app.exchangerate.apiprovider.ApiProvider;
import com.finance.app.exchangerate.boundary.ExchangeRateResponse;
import com.finance.app.exchangerate.domain.enums.CurrencyCode;
import com.finance.app.exchangerate.domain.enums.RegionCode;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

/**
 * API provider - National Bank of Russia.
 */
@Component
public class RussiaProviderClient extends ApiProvider {
    private final SOAPConnectionFactory soapConnectionFactory;
    private final RussiaNationalBankProcess russiaNationalBankProcess;

    @Value("${api-provider.national-bank-russia}")
    public String URL;

    public RussiaProviderClient(SOAPConnectionFactory soapConnectionFactory, RussiaNationalBankProcess russiaNationalBankProcess) {
        super(RegionCode.RU);
        this.soapConnectionFactory = soapConnectionFactory;
        this.russiaNationalBankProcess = russiaNationalBankProcess;
    }

    // TODO add convert to response
    // TODO add try catch switch to common + auto close connection
    public SOAPMessage fetchCurrencyRate(LocalDate byDate) throws SOAPException, IOException, JAXBException {
        SOAPConnection newSoapConnection = soapConnectionFactory.createConnection();
        final var message = russiaNationalBankProcess.buildSoapMessage(byDate);
        SOAPMessage soapResponse = newSoapConnection.call(message, URL);
        final var response = russiaNationalBankProcess.mapToGetCursOnDateXMLResponse(soapResponse);
        newSoapConnection.close();
        return soapResponse;
    }

    @Override
    protected ExchangeRateResponse fetchExchangeRate(LocalDate byDate, CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        return null;
    }
}
