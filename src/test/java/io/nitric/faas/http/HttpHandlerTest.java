package io.nitric.faas.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpHandlerTest {

    @Test
    public void test_toString() throws IOException {
        var request = HttpRequest.newBuilder().method("GET").path("/mycorp/customer").build();

        var response = new MyHttpHandler().handle(request);

        assertEquals(301, response.getStatusCode());
    }

    public class MyHttpHandler implements HttpHandler {

        @Override
        public HttpResponse handle(HttpRequest request) {
            return HttpResponse.newBuilder().statusCode(301).build();
        }
    }
}
