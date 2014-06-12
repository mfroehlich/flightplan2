/**
 * 
 */
package com.prodyna.pac.flightplan.common.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Interceptor
@Logging
public class LoggingInterceptor {

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object logMethodCall(InvocationContext ctx) throws Exception {

        Method method = ctx.getMethod();

        logger.debug("Calling method " + method.getName());

        Object returnValue = ctx.proceed();

        logger.debug("Returning method " + method.getName() + ", return value: " + returnValue);

        return returnValue;
    }
}
