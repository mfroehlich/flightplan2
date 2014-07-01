package com.prodyna.stream.backend.mongo.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.prodyna.stream.backend.mongo.Channel;
import com.prodyna.stream.backend.mongo.Tweet;
import com.prodyna.stream.backend.mongo.TweetService;
import com.prodyna.stream.backend.mongo.User;
import com.prodyna.stream.backend.mongo.common.hash.Hasher;
import org.apache.commons.codec.digest.DigestUtils;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Arrays;

@Stateless
@Path("/test")
public class ScenarioSupporterBean {

    @Inject
    private Logger log;

    @Inject
    private DB db;

    @Inject
    private Datastore ds;

    @Inject
    private TweetService ts;

    @Inject
    private Hasher hasher;

    @GET
    @Path("/reset")
    public void resetScenario() {
        log.warn("Resetting scenario");

        DBCollection meta = db.getCollection("meta");

        meta.drop();
        meta.insert(new BasicDBObject(new BasicDBObject("_id", "name").append("value", "read-from-mongo")));

        // we need to handle channels directly, because there is no service for that
        ds.getCollection(Channel.class).drop();

        Channel hr = new Channel(1, "hr", "Human Resources", false, false);
        ds.save(hr);

        Channel dev = new Channel(2, "dev", "Development", false, false);
        ds.save(dev);

        Channel cas = new Channel(3, "cas", "Casual", false, false);
        ds.save(cas);

        log.warn("Created " + ds.find(Channel.class));

        // we need to handle users directly, because there is no service for that
        ds.getCollection(User.class).drop();


        String hashed = hasher.hash("abc123".getBytes());
        User dkrizic = new User("dkrizic", "Darko Krizic", "bla", hashed, new ArrayList<Channel>());
        ds.save(dkrizic);

        User mjahn = new User("mjahn", "Marco Jahn", "bla", hashed, new ArrayList<Channel>());
        ds.save(mjahn);

        User hsteinauer = new User("hsteinauer", "Holger Steinauer", "bla", hashed, new ArrayList<Channel>());
        ds.save(hsteinauer);

        log.warn("Created " + ds.find(User.class));

        ds.getCollection(Tweet.class).drop();

        // tweet
        Tweet tweet = new Tweet();
        tweet.setAuthor(dkrizic);
        tweet.setChannels(Arrays.asList(new String[]{dev.getKey()}));
        tweet.setCommentsCount(0);
        tweet.setCreated("42");
        tweet.setId("42");
        tweet.setText("This is the text of the tweet");
        tweet.setUpdated("43");
        tweet.setUserLiked(false);
        ds.save(tweet);

        log.warn("Created " + ds.find(Tweet.class));

    }

}
