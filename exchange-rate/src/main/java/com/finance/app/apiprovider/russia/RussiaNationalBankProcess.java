package com.finance.app.apiprovider.russia;

import com.finance.app.boundary.GetCursOnDateXMLResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RussiaNationalBankProcess {
    private final MessageFactory messageFactory;
    private final JAXBContext jaxbContext;

    public SOAPMessage buildSoapMessage(LocalDate toMessage) throws SOAPException, IOException {
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Get the SOAP envelope and body
        SOAPEnvelope envelope = soapPart.getEnvelope();

        // Set the prefix to "soap12" instead of the default "env"
        envelope.removeNamespaceDeclaration(envelope.getPrefix());
        envelope.setPrefix("soap12");

        // Set namespaces
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");

        // Create SOAP body
        SOAPBody soapBody = envelope.getBody();
        soapBody.setPrefix("soap12");

        // Remove the SOAP header
        soapMessage.getSOAPHeader().detachNode();

        // Create main element with namespace
        SOAPElement getCursOnDateXML = soapBody.addChildElement("GetCursOnDateXML", "", "http://web.cbr.ru/");

        // Add child element with the date
        SOAPElement onDate = getCursOnDateXML.addChildElement("On_date");
        onDate.addTextNode(toMessage.toString());

        // Convert SOAP message to string with XML declaration
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n".getBytes("UTF-8"));
        soapMessage.writeTo(outputStream);

        soapMessage.saveChanges(); // Save the message to finalize changes

        // LOG, TODO later change it to Slf4j
        System.out.println("SOAP Request:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    public GetCursOnDateXMLResponse mapToGetCursOnDateXMLResponse(SOAPMessage source) throws SOAPException, JAXBException {
        SOAPBody soapBody = source.getSOAPBody();
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(soapBody.extractContentAsDocument(), GetCursOnDateXMLResponse.class).getValue();
    }
}
