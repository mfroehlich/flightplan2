package com.prodyna.stream.backend.mongo;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The user service. The interface contains JAX-RS annotations an can be used for dynamic proxies.
 */
@Local
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserService {

    @GET
    @Path("{userIds}")
    public List<User> getUserInfos( @PathParam("userIds") String userIds );

    @GET
    @Path("{userId}/avatar")
    public byte[] getUserAvatar( @PathParam("userId") long userId );

}
