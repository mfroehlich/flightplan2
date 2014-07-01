package com.prodyna.stream.backend.mongo.common.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * Intercentor that logs each method call and can be used on Session Beans.
 */
@Interceptor
@Monitored
public class MonitoringInterceptor {
    private static final String WELD = "$Proxy$";
    private static final boolean REPORT = true;
    public static boolean logEnabled = true;

    @AroundInvoke
    public Object transformReturn(InvocationContext context) throws Exception {
        String target = context.getTarget().getClass().getName();
        if (target.contains(WELD)) {
            int i = target.indexOf(WELD);
            target = target.substring(0, i);
        }
        Logger log = LoggerFactory.getLogger(target);
        Method m = context.getMethod();
        boolean v = m.getReturnType().equals(Void.TYPE);
        String line = m.getName() + params(context);
        if (logEnabled) {
            log.info(">>> " + line);
        }
        long before = System.currentTimeMillis();
        Object ret;
        try {
            ret = context.proceed();
        } catch (Exception e) {
            if (logEnabled) {
                log.info("<<< " + line + " ! " + e.getClass().getName());
            }
            throw e;
        }
        long after = System.currentTimeMillis();
        long diff = after - before;
        String retout = "" + ret;
        if (retout.length() > 200) {
            retout = retout.substring(0, 200) + "...";
        }
        if (logEnabled) {
            log.info("<<< " + line + " {" + diff + "} " + (v ? "" : ret));
        }
        if (REPORT) {
            String sn = context.getTarget().getClass().getSimpleName();
            if (sn.contains(WELD)) {
                int i = sn.indexOf(WELD);
                sn = sn.substring(0, i);
            }
        }
        return ret;
    }

    private String params(InvocationContext context) {
        StringBuffer sb = new StringBuffer();
        for (Object p : context.getParameters()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(p == null ? "null" : p.toString());
        }
        String out = sb.toString();
        if (out.length() > 200) {
            out = sb.substring(0, 199) + "...";
        }
        return "(" + out + ")";
    }
}