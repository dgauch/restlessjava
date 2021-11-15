package dev.gauch.restlessjava.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("memory")
public interface MemoryResourceClient {

    @POST
    Response consume(
        @QueryParam("amount-int") Long amountInt
    );
    
    @GET
    @Path("stats")
    Response stats();

    @POST
    @Path("gc")
    Response gc();
    
}