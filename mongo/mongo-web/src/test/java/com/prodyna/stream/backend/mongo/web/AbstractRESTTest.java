package com.prodyna.stream.backend.mongo.web;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;

public class AbstractRESTTest {

    public static final String USERNAME = "dkrizic";
    public static final String PASSWORD = "abc123";

    @ArquillianResource
    protected java.net.URL url;

    protected Client createClient() {
        final Client client = ClientBuilder.newClient();
        client.register(JsonProcessingFeature.class); // needed for "normal" clients
        client.register(JacksonFeature.class);      // needed for dynamic proxy
        client.register(new ClientAuthenticationSetter(USERNAME, PASSWORD));
        return client;
    }

    protected WebTarget createWebTarget() {
        WebTarget target = createClient().target(url.toString() + "rest");
        return target;
    }

    protected <C> C createService(Class<C> ifaceType) {
        return WebResourceFactory.newResource(ifaceType, createWebTarget());
    }

}