/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodCallsMonitored {

}
