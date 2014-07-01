package com.prodyna.stream.backend.mongo.web;

import com.mongodb.util.Base64Codec;
import com.prodyna.stream.backend.mongo.common.security.UserCredentials;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Reads the HTTP header "Authoration" (HTTP Basic Authententicatio), extracts the data and stores it in UserCredential which is passed with the request.
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private Logger log;

    @Inject
    private UserCredentials userCredentials;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String auth = containerRequestContext.getHeaderString("Authorization");

        if( auth == null ) {
            // no header set - return
            return;
        }

        if( ! auth.startsWith("Basic ") ) {
            // no basic authentication
            return;
        }

        auth = auth.substring("Basic ".length() );
        auth = new String( new Base64Codec().decode( auth ) );
        String[] vals = auth.split(":");
        userCredentials.setUsername( vals[0] );
        userCredentials.setPassword( vals[1] );

        log.debug("Found Authorization for user " + userCredentials.getUsername() );
    }
}
