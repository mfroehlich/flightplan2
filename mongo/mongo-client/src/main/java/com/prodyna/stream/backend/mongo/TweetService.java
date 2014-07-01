package com.prodyna.stream.backend.mongo;

import javax.ejb.Local;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The service for Tweets.
 */
@Local
@Path("/tweet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TweetService {

    @GET
    @Path("/{tweetId}")
    public Tweet getTweetById( @PathParam("tweetId") String tweetId );

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Tweet createTweet( @FormParam("text") String text, @FormParam("channelId") String channelId, @FormParam("") byte[] files );

    @DELETE
    @Path("/{tweetId}")
    public void deleteTweet( @PathParam("tweetId") String tweetId );

    @PUT
    @Path("/{tweetId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void updateTweet( @PathParam("tweetId") String tweetId, @FormParam("text") @Max(140) String text, @FormParam("channelId") String channelId );

    @GET
    @Path("/channel/{channelIds}")
    public List<Tweet> getTweetsForChannels( @PathParam("channelIds") String channelIds, @QueryParam("start") @DefaultValue("0") long start, @QueryParam("limit") @DefaultValue("15") long limit );

    @GET
    @Path("/author/{authorId}")
    public List<Tweet> getTweetsForAuthor( @PathParam("authorId") String authorId, @QueryParam("start") @DefaultValue("0") long start, @QueryParam("limit") @DefaultValue("15") long limit );

    @GET
    @Path("/since/{timestamp}")
    public List<Tweet> getTweetsSinceTimeStamp( @PathParam("timestamp") String timestamp, @QueryParam("start") @DefaultValue("0") long start, @QueryParam("limit") @DefaultValue("15") long limit );

    @POST
    @Path("{tweetId}/attachment")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addAttachmentToTweet( @PathParam("tweetId") String tweetId, @FormParam("files") byte[] files );

    @POST
    @Path("{tweetId}/comment")
    public Comment createTweetComment( @PathParam("tweetId") String tweetId, String text );

    @DELETE
    @Path("/comment/{commentId}")
    public void deleteTweetComment( @PathParam("commentId") String commentId );

    @GET
    @Path("{tweetId}/comments")
    public List<Comment> readTweetComments( @PathParam("tweetId") String tweetId );

    @POST
    @Path("/tweet/{tweetId}/like")
    public void createTweetLike( @PathParam("tweetId") String tweetId);

    @DELETE
    @Path("/tweet/{tweetId}/like")
    public void deleteTweetLike( @PathParam("tweetId") String tweetId);

    @GET
    @Path("/tweet/{tweetId}/like")
    public List<Like> getTweetLikes( @PathParam("tweetId") String tweetId);
}
