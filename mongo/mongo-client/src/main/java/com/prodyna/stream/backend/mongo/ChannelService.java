package com.prodyna.stream.backend.mongo;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The Channel service. Contains JAX-RS annotations and can be used for dynamic proxy.
 */
@Local
@Path("/channel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ChannelService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Channel> getAllAvailableChannels();

    @GET
    @Path("/subscription")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Channel> getSubscribedChannels();

    @POST
    @Path("/{channelId}/subscription")
    @Produces(MediaType.APPLICATION_JSON)
    public void subscribeChannel( @PathParam("channelId") long channelId );

    @DELETE
    @Path("/{channelId}/subscription")
    @Produces(MediaType.APPLICATION_JSON)
    public void unsubscribeChannel( @PathParam("channelId") long channelId );

    @PUT
    @Path("/subscription/{channelIds}")
    public void updateChannelSubscriptions( @PathParam("channelIds") String channelIds );

}
