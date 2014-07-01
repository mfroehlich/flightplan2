/**
 * 
 */
package com.prodyna.pac.flightplan.common.interceptor;

import java.lang.reflect.Method;
import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.exception.UserNotAuthorizedException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AuthorizationInterceptor {

    @Resource
    private SessionContext sessionContext;

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object checkUserAuthorization(InvocationContext ctx) throws Exception {

        boolean userHasRequiredRoleAssigned = false;
        Method method = ctx.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();

        logger.debug("Checking authorization for user " + getCallerPrincipal() + " calling method "
                + declaringClass.getCanonicalName() + "#" + method.getName());

        AuthorizedRoles[] classAnnotations = declaringClass.getAnnotationsByType(AuthorizedRoles.class);
        if (classAnnotations != null && classAnnotations.length > 0) {
            AuthorizedRoles classAnnotation = classAnnotations[0];

            String[] classRoles = classAnnotation.value();
            if (isCallerInRole(classRoles)) {
                userHasRequiredRoleAssigned = true;
            }
        }

        if (userHasRequiredRoleAssigned == false) {
            AuthorizedRoles[] methodAnnotations = method.getAnnotationsByType(AuthorizedRoles.class);
            if (methodAnnotations != null && methodAnnotations.length > 0) {
                AuthorizedRoles methodAnnotation = methodAnnotations[0];

                String[] methodRoles = methodAnnotation.value();
                if (isCallerInRole(methodRoles)) {
                    userHasRequiredRoleAssigned = true;
                }
            }
        }

        Object returnValue;

        if (userHasRequiredRoleAssigned == true) {
            logger.debug("User " + getCallerPrincipal() + " may call method " + declaringClass.getCanonicalName() + "#"
                    + method.getName());
            returnValue = ctx.proceed();
        } else {
            logger.debug("PERMISSION DENIED! User " + getCallerPrincipal() + " may not call method "
                    + declaringClass.getCanonicalName() + "#" + method.getName());

            throw new UserNotAuthorizedException();
        }

        return returnValue;
    }

    private boolean isCallerInRole(String... roles) {
        boolean isCallerInRole = false;

        for (String role : roles) {
            if (role.equals("GUEST")) {
                isCallerInRole = true;
                break;
            }
        }
        if (isCallerInRole == false) {
            for (String role : roles) {
                if (sessionContext.isCallerInRole(role)) {
                    isCallerInRole = true;
                    break;
                }
            }
        }

        return isCallerInRole;
    }

    private String getCallerPrincipal() {
        Principal callerPrincipal = sessionContext.getCallerPrincipal();
        String name = callerPrincipal == null ? "anonymous user" : callerPrincipal.getName();
        return name;
    }
}
