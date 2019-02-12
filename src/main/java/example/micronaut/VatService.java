package example.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Single;

import javax.inject.Singleton;

@Singleton // <1>
public class VatService {
    private static final String SERVER = "http://ec.europa.eu";
    private static final String PATH = "/taxation_customs/vies/services/checkVatService";
    private static final String VALID_XML_OPEN_TAG = "<valid>";
    private static final String VALID_XML_CLOSE_TAG = "</valid>";

    protected final RxHttpClient client;

    public VatService(@Client("http://ec.europa.eu") RxHttpClient client) { // <2>
        this.client = client;
    }

    public Single<Boolean> validateVat(String memberStateCode, String vatNumber) {
        String soapEnvelope = soapEnvelope(memberStateCode, vatNumber);
        HttpRequest request = HttpRequest.POST(SERVER+PATH, soapEnvelope)  // <3>
                .contentType("application/soap+xml");

        Flowable<String> response = client.retrieve(request, String.class);
        return response.firstOrError().map(this::parseResponseToBoolean);
    }

    private Boolean parseResponseToBoolean(String response) {
        if (!response.contains(VALID_XML_OPEN_TAG) || !response.contains(VALID_XML_CLOSE_TAG)) {
            return false;
        }
        int beginIndex = response.indexOf(VALID_XML_OPEN_TAG) + VALID_XML_OPEN_TAG.length();
        String validResponse = response.substring(beginIndex, response.indexOf(VALID_XML_CLOSE_TAG));
        return Boolean.valueOf(validResponse);
    }

    private String soapEnvelope(String memberStateCode, String vatNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">");
        sb.append("<urn:countryCode>");
        sb.append(memberStateCode);
        sb.append("</urn:countryCode>");
        sb.append("<urn:vatNumber>");
        sb.append(vatNumber);
        sb.append("</urn:vatNumber>");
        sb.append("</urn:checkVat>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");
        return sb.toString();
    }
}