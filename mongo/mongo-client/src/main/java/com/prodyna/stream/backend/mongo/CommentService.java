package com.prodyna.stream.backend.mongo;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * The Comment service. Interface contains JAX-RS annotations and can be used for dynamic proxy.
 */
@Local
@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CommentService {

    @POST
    @Path("{commentId}/like")
    public void addCommentLike( @PathParam("commentId") String commentId );

    @DELETE
    @Path("{commentId}/like")
    public void deleteCommentLike( @PathParam("commentId") String commentId );

    @GET
    @Path("{commentId}/like")
    public void readTweetLikes( @PathParam("commentId") String commentId );


}
