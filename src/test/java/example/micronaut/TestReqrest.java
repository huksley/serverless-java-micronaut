package example.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestReqrest {
    Logger log = LoggerFactory.getLogger(TestReqrest.class);

    @Test
    public void testApi() {
        HttpRequest<String> request = HttpRequest.GET("https://reqres.in/api/users");
        log.info("Got request: {}", request);
        RxHttpClient client = new
        request.g
    }
}
