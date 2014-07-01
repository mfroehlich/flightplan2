package com.prodyna.stream.backend.mongo.web;

import com.prodyna.stream.backend.mongo.Application;
import com.prodyna.stream.backend.mongo.ApplicationService;
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
public class ApplicationServiceRESTTest extends AbstractRESTTest {

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
    public void applicationInfo() {
        ApplicationService as = createService(ApplicationService.class);
        Application ai = as.getApplicationInfo();
        Assert.assertEquals("read-from-mongo", ai.getName());
    }
}
