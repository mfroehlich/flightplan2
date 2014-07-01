package com.prodyna.stream.backend.mongo;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service with details about the application. Contains JAX-RS annotations and can be used for a dynamic proxy.
 */
@Local
@Path("/application")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ApplicationService {

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Application getApplicationInfo();

    @GET
    @Path("/info/icon")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getApplicationIcon();

    @GET
    @Path("/info/logo")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getApplicationLogo();

}
