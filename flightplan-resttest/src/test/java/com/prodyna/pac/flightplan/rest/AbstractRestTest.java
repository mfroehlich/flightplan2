/**
 * 
 */
package com.prodyna.pac.flightplan.rest;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.jboss.arquillian.test.api.ArquillianResource;

/**
 * Abstract super class for REST tests to create the dynamic proxy for the service class to be called via REST.
 * 
 * @author mfroehlich
 *
 */
public class AbstractRestTest {

    @ArquillianResource
    private URL url;

    protected WebTarget createWebTarget() {

        final Client client = ClientBuilder.newClient();
        String uri = url.toString() + "restapp";
        WebTarget target = client.target(uri);

        return target;
    }

    protected <C> C createService(Class<C> ifaceType) {
        return WebResourceFactory.newResource(ifaceType, createWebTarget());
    }
}
