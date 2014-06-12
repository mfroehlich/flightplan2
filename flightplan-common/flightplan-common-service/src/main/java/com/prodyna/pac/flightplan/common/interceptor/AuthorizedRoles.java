/**
 * 
 */
package com.prodyna.pac.flightplan.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizedRoles {

    public String[] value();
}
