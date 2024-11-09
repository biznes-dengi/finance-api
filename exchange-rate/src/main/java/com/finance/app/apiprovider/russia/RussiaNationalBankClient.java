package com.finance.app.apiprovider.russia;

import com.finance.app.boundary.GetCursOnDateXMLResponse;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

/**
 * API provider - National Bank of Russia.
 */
@Component
@RequiredArgsConstructor
public class RussiaNationalBankClient {

    public final static String regionCode = "RU";

    @Value("${api-provider.national-bank-russia}")
    public String URL;

    private final SOAPConnectionFactory soapConnectionFactory;

    private final RussiaNationalBankProcess russiaNationalBankProcess;

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

}
