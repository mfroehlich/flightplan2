package com.prodyna.stream.backend.mongo.web;

import com.prodyna.stream.backend.mongo.Tweet;
import com.prodyna.stream.backend.mongo.TweetService;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RunWith(Arquillian.class)
public class TweetServiceRESTTest extends AbstractRESTTest {

    @Test
    @RunAsClient
    @InSequence(1)
    public void resetScenario() {
        // this part is without dynamic proxy
        WebTarget target = createWebTarget();
        Response resp = target.path("/test/reset").request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertEquals(204, resp.getStatus());
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void readFirstTweet() {
        TweetService ts = createService(TweetService.class);
        Tweet tweet = ts.getTweetById("42");
        Assert.assertEquals("This is the text of the tweet", tweet.getText() );
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void readFirstTweetCheckJson() {
        WebTarget target = createWebTarget();
        JsonObject tweet = target.path("/tweet/42").request(MediaType.APPLICATION_JSON_TYPE).get(JsonObject.class);
        System.out.println( tweet );
        Assert.assertEquals("This is the text of the tweet", tweet.getString("text") );
        Assert.assertTrue( tweet.containsKey("author"));
        Assert.assertTrue( tweet.containsKey("text"));
        Assert.assertTrue( tweet.containsKey("created"));
        Assert.assertTrue( tweet.containsKey("updated"));
        Assert.assertFalse( tweet.containsKey("likes"));
    }

}
