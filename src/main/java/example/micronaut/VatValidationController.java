package example.micronaut;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.http.annotation.*;

@Controller("/vat")
public class VatValidationController {
    private static final Logger LOG = LoggerFactory.getLogger(VatValidationController.class); // <3>

    private final VatService vatService;

    public VatValidationController(VatService vatService) { // <4>
        this.vatService = vatService;
    }

    @Get("/ping")
    public String index() {
        return "{\"pong\":true, \"graal\": true}";
    }

    @Post("/validate")
    public VatValidation validate(@Body String s) throws IOException {
        ObjectMapper om = new ObjectMapper();
        VatValidationRequest request = om.readValue(s, VatValidationRequest.class);
        final String memberStateCode = request.getMemberStateCode();
        final String vatNumber = request.getVatNumber();
        if (LOG.isDebugEnabled()) {
            LOG.debug("validate country: {} vat number: {}", memberStateCode, vatNumber);
        }
        return vatService.validateVat(memberStateCode, vatNumber)
                .map(valid -> new VatValidation(memberStateCode, vatNumber, valid)).blockingGet(); // <5>
    }

    @Post("/validateApprox")
    public VatValidation validateApprox(@Body String s) throws IOException {
        ObjectMapper om = new ObjectMapper();
        VatValidationRequest request = om.readValue(s, VatValidationRequest.class);
        final String memberStateCode = request.getMemberStateCode();
        final String vatNumber = request.getVatNumber();
        final String requesterMemberStateCode = request.getRequesterVatNumber();
        final String requesterVatNumber = request.getRequesterVatNumber();
        if (LOG.isDebugEnabled()) {
            LOG.debug("validate country: {} vat number: {}, requester country {}, vat {}", memberStateCode, vatNumber, requesterMemberStateCode, requesterVatNumber);
        }
        return vatService.validateVatApprox(memberStateCode, vatNumber, requesterMemberStateCode, requesterVatNumber)
                .map(valid -> new VatValidation(memberStateCode, vatNumber, valid)).blockingGet(); 
    }
}
