package dev.gauch.restlessjava.boundary;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    public void burnCold1Thread() {
        Response response = this.client.burnCold(null, null, null, null);
        int status = response.getStatus();
        assertEquals(200, status);

        assertNotNull(response.getHeaderString("delay-time-ms-0"));
        assertNull(response.getHeaderString("delay-time-ms-1"));
    }

    @Test
    public void burnCold5Threads() {
        Response response = this.client.burnCold(null, null, 5, 5);
        int status = response.getStatus();
        assertEquals(200, status);

        assertNotNull(response.getHeaderString("delay-time-ms-0"));
        assertNotNull(response.getHeaderString("delay-time-ms-1"));
        assertNotNull(response.getHeaderString("delay-time-ms-2"));
        assertNotNull(response.getHeaderString("delay-time-ms-3"));
        assertNotNull(response.getHeaderString("delay-time-ms-4"));
    }

    @Test
    public void burnHot1Thread() {
        Response response = this.client.burnHot(null, null, null, null);
        int status = response.getStatus();
        assertEquals(200, status);

        assertNotNull(response.getHeaderString("delay-time-ms-0"));
        assertNull(response.getHeaderString("delay-time-ms-1"));
    }

    @Test
    public void burnHot5Threads() {
        Response response = this.client.burnHot(null, null, 5, 5);
        int status = response.getStatus();
        assertEquals(200, status);

        assertNotNull(response.getHeaderString("delay-time-ms-0"));
        assertNotNull(response.getHeaderString("delay-time-ms-1"));
        assertNotNull(response.getHeaderString("delay-time-ms-2"));
        assertNotNull(response.getHeaderString("delay-time-ms-3"));
        assertNotNull(response.getHeaderString("delay-time-ms-4"));
    }
}