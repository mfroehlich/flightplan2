package com.prodyna.stream.backend.mongo.web;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * A basic filter that does some logging.
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter {

    @Inject
    private Logger log;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String out = String.format("%s %s", ctx.getMethod(), ctx.getUriInfo().getPath() );
        log.info( out );
    }
}
