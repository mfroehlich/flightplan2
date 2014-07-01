package com.prodyna.stream.backend.mongo.common.monitoring;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Intercentor binding for the MontoringInternceptor.
 */
@Inherited
@InterceptorBinding
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitored {
    // ok
}
