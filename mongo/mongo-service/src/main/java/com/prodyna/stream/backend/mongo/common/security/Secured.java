package com.prodyna.stream.backend.mongo.common.security;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Interceptor binding for all services that need to be Authentication protected.
 */
@Inherited
@InterceptorBinding
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    // ok
}
