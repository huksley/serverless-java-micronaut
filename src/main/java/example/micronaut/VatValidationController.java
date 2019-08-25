package example.micronaut;

import com.agorapulse.micronaut.aws.dynamodb.DynamoDBService;
import com.agorapulse.micronaut.aws.dynamodb.DynamoDBServiceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.BeanContext;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;

@Controller("/vat")
public class VatValidationController {
    private static final Logger LOG = LoggerFactory.getLogger(VatValidationController.class); // <3>

    private final VatService vatService;
    private final BeanContext context;

    public VatValidationController(VatService vatService, BeanContext context) { // <4>
        this.vatService = vatService;
        this.context = context;
    }



    @Get("/ping")
    public String index() throws IOException {

        DynamoDBServiceProvider provider = context.getBean(DynamoDBServiceProvider.class);
        DynamoDBService<Todo> s = provider.findOrCreate(Todo.class);

        s.createTable(1L, 1L);
        Todo todo = s.getNewInstance();
        todo.setId(UUID.randomUUID().toString());
        todo.setText("Hello from micronaut " + System.currentTimeMillis());
        todo.setDone(false);
        s.save(todo);

        return "{\"pong\":true, \"graalvm\": true, \"todos\": \"" + s.get(todo.getId()).getText() + "\"}";
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
