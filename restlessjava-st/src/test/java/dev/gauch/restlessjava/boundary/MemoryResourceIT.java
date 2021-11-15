package dev.gauch.restlessjava.boundary;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryResourceIT {

    private MemoryResourceClient client;

    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:8080/restlessjava/api/");
        this.client = RestClientBuilder.newBuilder().baseUri(uri).build(MemoryResourceClient.class);

    }

    @Test
    public void getStats() {
        Response response = this.client.stats();
        int status = response.getStatus();
        assertEquals(200, status);

        JsonStructure jsonObject = Json.createReader(new StringReader(response.readEntity(String.class))).read();
        assertTrue(jsonObject.asJsonObject().keySet().contains("totalMemoryInMib"));
        assertTrue(jsonObject.asJsonObject().keySet().contains("freeMemoryInMib"));
        assertTrue(jsonObject.asJsonObject().keySet().contains("maxMemoryInMib"));
    }

    @Test
    public void triggerGc() {
        Response response = this.client.gc();
        assertEquals(202, response.getStatus());

        JsonStructure jsonObject = Json.createReader(new StringReader(response.readEntity(String.class))).read();
        assertTrue(jsonObject.asJsonObject().keySet().contains("before"));
        assertTrue(jsonObject.asJsonObject().keySet().contains("after"));
    }

    @Test
    public void consumeMemory() {
        Response response = this.client.consume(1000l);
        assertEquals(202, response.getStatus());

        JsonStructure jsonObject = Json.createReader(new StringReader(response.readEntity(String.class))).read();
        assertEquals(1000, jsonObject.asJsonObject().getInt("amount"));
        assertTrue(jsonObject.asJsonObject().keySet().contains("maxValue"));
        assertTrue(jsonObject.asJsonObject().keySet().contains("minValue"));
    }


    @Test
    public void invalidArguments() {
        assertThrows(WebApplicationException.class, () -> this.client.consume(null));
    }
}