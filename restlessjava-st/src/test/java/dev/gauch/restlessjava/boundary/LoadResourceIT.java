package dev.gauch.restlessjava.boundary;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadResourceIT {

    private LoadResourceClient client;

    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:8080/restlessjava/api/");
        this.client = RestClientBuilder.newBuilder().baseUri(uri).build(LoadResourceClient.class);

    }

    @Test
    public void burnCold() {
        Response response = this.client.burnCold(null, null);
        int status = response.getStatus();
        assertEquals(200, status);

        String delayTimeInMs = response.getHeaderString("delay-time-ms");
        assertNotNull(delayTimeInMs);
    }
}