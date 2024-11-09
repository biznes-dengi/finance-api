package com.finance.app.config;

import com.finance.app.boundary.GetCursOnDateXMLResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class Config {

    @Value("${api-provider.common}")
    public String baseUrlCommon;

//    @Bean
//    public Jaxb2Marshaller marshaller(DateXmlAdapter dateXmlAdapter) {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(GetCursOnDateXML.class, GetCursOnDateXMLResponse.class);
//        marshaller.setAdapters(dateXmlAdapter);
//        //marshaller.setContextPath(contextPath);
//        return marshaller;
//    }

//    @Bean
//    public RussiaProvider russiaProvider(Jaxb2Marshaller marshaller, SoapClientInterceptor soapClientInterceptor) {
//        RussiaProvider russiaProvider = new RussiaProvider();
//        russiaProvider.setDefaultUri("http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx");
//        russiaProvider.setMarshaller(marshaller);
//        russiaProvider.setUnmarshaller(marshaller);
//
//        ClientInterceptor[] interceptors = new ClientInterceptor[] {soapClientInterceptor};
//        russiaProvider.setInterceptors(interceptors);
//
//        return russiaProvider;
//    }

//    @Bean
//    public SoapClientInterceptor soapInterceptor() {
//        return new SoapClientInterceptor();
//    }
//
//    @Bean
//    public DateXmlAdapter dateXmlAdapter() {
//        return new DateXmlAdapter();
//    }

    @Bean
    public MessageFactory messageFactory() throws SOAPException {
        return MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
    }

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(GetCursOnDateXMLResponse.class);
    }

    @Bean
    public SOAPConnectionFactory soapConnectionFactory() throws SOAPException {
        return SOAPConnectionFactory.newInstance();
    }

    @Bean
    public RestClient restClient() {
       return RestClient.builder()
                .baseUrl(baseUrlCommon)
                .build();
    }
}
