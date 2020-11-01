package dev.gauch.restlessjava.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.IntBinaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import dev.gauch.restlessjava.control.CpuBurner;
import dev.gauch.restlessjava.entity.RandomString;

@Path("load")
public class LoadResource {

    private static final Logger LOG = Logger.getLogger(LoadResource.class.getName());

    @Inject
    @ConfigProperty(name = "load.default.time.min", defaultValue = "100")
    Integer defaultTimeMin;

    @Inject
    @ConfigProperty(name = "load.default.time.max", defaultValue = "500")
    Integer defaultTimeMax;

    @Inject
    @ConfigProperty(name = "load.response.size", defaultValue = "10000")
    Long loadResponseSize;

    @Resource
    ManagedExecutorService mes;

    @GET
    @Path("burn-cold")
    public Response burnCold(@QueryParam("min") Integer min, @QueryParam("max") Integer max,
            @QueryParam("minThreads") Integer minThreads, @QueryParam("maxThreads") Integer maxThreads) {
        return load(min, max, minThreads, maxThreads, CpuBurner::burnCold);
    }

    @GET
    @Path("burn-hot")
    public Response burnHot(@QueryParam("min") Integer min, @QueryParam("max") Integer max,
            @QueryParam("minThreads") Integer minThreads, @QueryParam("maxThreads") Integer maxThreads) {
        return load(min, max, minThreads, maxThreads, CpuBurner::burnHot);
    }

    private Response load(Integer min, Integer max, Integer minThreads, Integer maxThreads, IntBinaryOperator load) {
        if (min == null) {
            min = max;
        }
        if (max == null) {
            max = min;
        }
        if (min == null) {
            min = defaultTimeMin;
            max = defaultTimeMax;
        }

        if (minThreads == null) {
            minThreads = maxThreads;
        }
        if (maxThreads == null) {
            maxThreads = minThreads;
        }
        if (minThreads == null) {
            minThreads = maxThreads = 1;
        }
        
        final int minTime = min;
        final int maxTime = max;
        
        int threadsCount = (maxThreads.equals(minThreads)) ? minThreads
                : minThreads + new Random().nextInt(maxThreads - minThreads);
        List<Callable<Integer>> threads = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            threads.add(() -> load.applyAsInt(minTime, maxTime));
        }

        try {
            List<Future<Integer>> threadResults = mes.invokeAll(threads);

            ResponseBuilder rb = Response.ok().entity(RandomString.getWithLettersAndNumbers(loadResponseSize));
            for (int i = 0; i < threadResults.size(); i++) {
                rb.header("delay-time-ms-" + i, threadResults.get(i).get());
            }

            return rb.build();

        } catch (InterruptedException | ExecutionException e) {
            LOG.log(Level.SEVERE, "Failed to sleep deeply", e);
            Thread.currentThread().interrupt();
            return Response.serverError().build();
        }
    }
}
