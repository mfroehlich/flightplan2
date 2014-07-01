package com.prodyna.stream.backend.mongo.web;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@RunWith(Arquillian.class)
public class MediaServiceRESTTest extends AbstractRESTTest {

    @Test
    @RunAsClient
    public void getAllAvailableChannels() {
        WebTarget target = createWebTarget();
        byte[] ba = target.path("/media/42").request(MediaType.APPLICATION_OCTET_STREAM).get(byte[].class);
        Assert.assertEquals("Hello, World", new String(ba));
    }

}
