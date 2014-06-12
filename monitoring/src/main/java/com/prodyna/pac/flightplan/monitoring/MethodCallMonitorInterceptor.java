/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Interceptor
@MethodCallsMonitored
public class MethodCallMonitorInterceptor {

    @Inject
    private MethodCallEventCollector collector;

    @AroundInvoke
    public Object addMethodCallToCountStatistic(InvocationContext ctx) throws Exception {

        long processingStart = System.currentTimeMillis();
        Object returnValue = ctx.proceed();
        long processingEnd = System.currentTimeMillis();

        long processingTime = processingEnd - processingStart;
        String methodSignature = getMethodSignature(ctx);
        MethodCallEvent event = new MethodCallEvent(methodSignature, processingTime);
        collector.receiveMethodCallEvent(event);

        return returnValue;
    }

    private String getMethodSignature(InvocationContext ctx) {

        StringBuilder signature = new StringBuilder();
        signature.append(ctx.getTarget().getClass().getCanonicalName());
        signature.append("#");
        signature.append(ctx.getMethod().getName());

        signature.append("(");
        Object[] parameters = ctx.getParameters();
        int count = 0;
        for (Object obj : parameters) {
            signature.append(obj.getClass().getName());
            if (++count < parameters.length) {
                signature.append(",");
            }
        }
        signature.append(")");

        return signature.toString();
    }
}
