package com.prodyna.stream.backend.mongo.common.security;

import com.prodyna.stream.backend.mongo.User;
import com.prodyna.stream.backend.mongo.common.hash.Hasher;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 * An alternative implementation of the AuthenticationValidator that uses the User entity and the contained hashed password to validate the Authentication.
 */
@Alternative
public class UserMongoAuthenticationValidator implements AuthenticationValidator {

    @Inject
    private Logger log;

    @Inject
    private UserCredentials userCredentials;

    @Inject
    private UserDetails userDetails;

    @Inject
    private Datastore ds;

    @Inject
    private Hasher hasher;

    @Override
    public void validate() {

        userDetails.setAuthenticated(false);
        if (userCredentials != null) {
            User user = ds.get(User.class, userCredentials.getUsername());
            if (user != null) {
                // user is found
                String entered = hasher.hash(userCredentials.getPassword().getBytes());
                String existing = user.getHashedPassword();
                if (entered.equals(existing)) {
                    userDetails.setAuthenticated(true);
                    userDetails.setUsername(userCredentials.getUsername());
                    log.info("Successfully authenticated " + userDetails);
                    return;
                }
                log.info("Password does not match");
                return;
            }
            log.info("User " + userCredentials.getUsername() + " not found");
        }
    }

}
