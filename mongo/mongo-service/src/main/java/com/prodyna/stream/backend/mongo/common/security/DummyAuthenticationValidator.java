package com.prodyna.stream.backend.mongo.common.security;

import org.slf4j.Logger;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * Alternative dummy implementation of the AuthenticationValidator interface.
 */
@Alternative
public class DummyAuthenticationValidator implements AuthenticationValidator {

    @Inject
    private Logger log;

    @Inject
    private UserCredentials userCredentials;

    @Inject
    private UserDetails userDetails;

    @Override
    public void validate() {
        userDetails.setAuthenticated(false);
        if (userCredentials != null) {
            if ("dkrizic".equals(userCredentials.getUsername())) {
                if ("secret".equals(userCredentials.getPassword())) {
                    userDetails.setAuthenticated(true);
                    userDetails.setUsername(userCredentials.getUsername());
                    log.info("Successfully authenticated " + userDetails);
                }
            }
        }
    }
}
