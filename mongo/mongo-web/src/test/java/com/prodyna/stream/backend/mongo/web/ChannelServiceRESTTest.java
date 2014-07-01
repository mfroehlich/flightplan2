package com.prodyna.stream.backend.mongo.web;

import com.prodyna.stream.backend.mongo.Channel;
import com.prodyna.stream.backend.mongo.ChannelService;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This call knows about the @see ScenarioSupporterBean that creates a Scenario in the database. The test methods are run in a specific sequence and base on each other.
 */
@RunWith(Arquillian.class)
public class ChannelServiceRESTTest extends AbstractRESTTest {

    @Test
    @RunAsClient
    @InSequence(1)
    public void resetScenario() {
        // this part is without dynamic proxy
        WebTarget target = createWebTarget();
        Response resp = target.path("/test/reset").request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertEquals( 204, resp.getStatus() );
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void getAllChannels() {
        ChannelService cs = createService(ChannelService.class);
        List<Channel> channels = cs.getAllAvailableChannels();
        assertEquals(3, channels.size());
        Assert.assertEquals("hr", channels.get(0).getKey());
        Assert.assertEquals("dev", channels.get(1).getKey());
        Assert.assertEquals("cas", channels.get(2).getKey());
        System.out.println("All channels: " + channels);
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void getSubscribedChannels() {
        ChannelService cs = createService(ChannelService.class);
        List<Channel> channels = cs.getSubscribedChannels();
        assertEquals(0, channels.size());
        System.out.println("Subscribed channel: " + channels);
    }

    @Test
    @RunAsClient
    @InSequence(4)
    public void subscribeChannel1() {
        ChannelService cs = createService(ChannelService.class);
        cs.subscribeChannel(1);
    }

    @Test
    @RunAsClient
    @InSequence(5)
    public void getSubscribedchannels2() {
        ChannelService cs = createService(ChannelService.class);
        List<Channel> channels = cs.getSubscribedChannels();
        assertEquals(1, channels.size());
        Assert.assertEquals("hr", channels.get(0).getKey());
        System.out.println("Subscribed channel: " + channels);
    }

    @Test
    @RunAsClient
    @InSequence(6)
    public void subscribeChannel2() {
        ChannelService cs = createService(ChannelService.class);
        cs.subscribeChannel(2);
    }

    @Test
    @RunAsClient
    @InSequence(7)
    public void getSubscribedchannels3() {
        ChannelService cs = createService(ChannelService.class);
        List<Channel> channels = cs.getSubscribedChannels();
        assertEquals(2, channels.size());
        Assert.assertEquals("hr", channels.get(0).getKey());
        Assert.assertEquals("dev", channels.get(1).getKey());
        System.out.println("Subscribed channel: " + channels);
    }

    @Test
    @RunAsClient
    @InSequence(8)
    public void unsubscribeChannel1() {
        ChannelService cs = createService(ChannelService.class);
        cs.unsubscribeChannel(1);
    }

    @Test
    @RunAsClient
    @InSequence(9)
    public void getSubscribedchannels4() {
        ChannelService cs = createService(ChannelService.class);
        List<Channel> channels = cs.getSubscribedChannels();
        assertEquals(1, channels.size());
        Assert.assertEquals("dev", channels.get(0).getKey());
        System.out.println("Subscribed channel: " + channels);
    }


}
