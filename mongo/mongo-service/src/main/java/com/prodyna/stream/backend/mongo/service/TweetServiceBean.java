package com.prodyna.stream.backend.mongo.service;

import com.prodyna.stream.backend.mongo.Comment;
import com.prodyna.stream.backend.mongo.Like;
import com.prodyna.stream.backend.mongo.Tweet;
import com.prodyna.stream.backend.mongo.TweetService;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.Secured;
import org.mongodb.morphia.Datastore;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Secured
@Monitored
@Stateless
public class TweetServiceBean implements TweetService {

    @Inject
    private Datastore ds;

    @Override
    public Tweet getTweetById(String tweetId) {
        return ds.get(Tweet.class, tweetId);
    }

    @Override
    public Tweet createTweet(String text, String channelId, byte[] files) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteTweet(String tweetId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateTweet(String tweetId, String text, String channelId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Tweet> getTweetsForChannels(String channelIds, long start, long limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Tweet> getTweetsForAuthor(String authorId, long start, long limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Tweet> getTweetsSinceTimeStamp(String timestamp, long start, long limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addAttachmentToTweet(String tweetId, byte[] files) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comment createTweetComment(String tweetId, String text) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteTweetComment(String commentId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Comment> readTweetComments(String tweetId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createTweetLike(String tweetId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteTweetLike(String tweetId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Like> getTweetLikes(String tweetId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
