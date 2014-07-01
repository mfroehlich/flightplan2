package com.prodyna.stream.backend.mongo.common.security;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Intercentor that checks the Authentication. It uses an AuthenticationValidator for doing the actual job and only checks if the request scoped UserDetails contain details after validation.
 */
@Secured
@Interceptor
public class AuthenticationInterceptor {

    @Inject
    private Logger log;

    @Inject
    private UserDetails userDetails;

    @Inject
    private AuthenticationValidator authenticationValidator;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        authenticationValidator.validate();
        if (userDetails.isAuthenticated()) {
            return ctx.proceed();
        }
        throw new NotAuthorizedException("Credentials missing or unable to validate");
    }

}
