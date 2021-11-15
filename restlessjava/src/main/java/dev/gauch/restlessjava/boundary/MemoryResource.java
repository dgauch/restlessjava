package dev.gauch.restlessjava.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("/memory")
public class MemoryResource {

    @POST
    public Response consume(@QueryParam("amount-int") @NotNull @Min(1) Long amountInt) {
        List<Integer> list = new Random().ints().limit(amountInt).sorted().boxed().collect(Collectors.toList());

        return Response.accepted(Json.createObjectBuilder()
                .add("amount", amountInt)
                .add("maxValue", list.get(0))
                .add("minValue", list.get(list.size()-1))
                .build()).build();
    }

    @GET()
    @Path("stats")
    public Response getStats() {
        return Response.ok(getMemoryStatsInJson()).build();
    }


    @POST
    @Path("gc")
    public Response runGc() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("before", getMemoryStatsInJson());
        System.gc();
        jsonObjectBuilder.add("after", getMemoryStatsInJson());
        return Response.accepted(jsonObjectBuilder.build()).build();
    }

    private JsonObject getMemoryStatsInJson() {
        return Json.createObjectBuilder()
                .add("totalMemoryInMib", Runtime.getRuntime().totalMemory() / 1024 / 1024)
                .add("freeMemoryInMib", Runtime.getRuntime().freeMemory() / 1024 / 1024)
                .add("maxMemoryInMib", Runtime.getRuntime().maxMemory() / 1024 / 1024)
                .build();
    }
}
