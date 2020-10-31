package dev.gauch.restlessjava.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import dev.gauch.restlessjava.control.CpuBurner;
import dev.gauch.restlessjava.entity.RandomString;

@Path("load")
public class LoadResource {

    @Inject
    @ConfigProperty(name = "load.default.time.min", defaultValue = "100")
    Integer defaultTimeMin;

    @Inject
    @ConfigProperty(name = "load.default.time.max", defaultValue = "500")
    Integer defaultTimeMax;

    @Inject
    @ConfigProperty(name = "load.response.size", defaultValue = "10000")
    Long loadResponseSize;

    @GET
    @Path("burn-cold")
    public Response burnCold(@QueryParam("min") Integer min, @QueryParam("max") Integer max) {
        if (min == null)
            min = max;
        if (min == null) {
            min = defaultTimeMin;
            max = defaultTimeMax;
        }
        int delayTimeMs = CpuBurner.burnCold(min, max);
        return Response.ok().entity(RandomString.getWithLettersAndNumbers(loadResponseSize)).header("delay-time-ms", ""+delayTimeMs).build();
    }
}
