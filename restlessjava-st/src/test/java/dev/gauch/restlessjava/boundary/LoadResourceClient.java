package dev.gauch.restlessjava.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("load")
public interface LoadResourceClient {

    @GET
    @Path("burn-cold")
    Response burnCold(@QueryParam("min") Integer min, @QueryParam("max") Integer max);
    
}