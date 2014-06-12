/**
 * 
 */
package com.prodyna.pac.flightplan.common.interceptor;

import java.lang.reflect.Method;

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

        logger.debug("Checking authorization for user " + sessionContext.getCallerPrincipal().getName()
                + " calling method " + declaringClass.getCanonicalName() + "#" + method.getName());

        AuthorizedRoles[] classAnnotations = declaringClass.getAnnotationsByType(AuthorizedRoles.class);
        if (classAnnotations != null && classAnnotations.length > 0) {
            AuthorizedRoles classAnnotation = classAnnotations[0];

            String[] classRoles = classAnnotation.value();
            for (String classRole : classRoles) {
                if (sessionContext.isCallerInRole(classRole)) {
                    userHasRequiredRoleAssigned = true;
                    break;
                }
            }
        }

        if (userHasRequiredRoleAssigned == false) {
            AuthorizedRoles[] methodAnnotations = method.getAnnotationsByType(AuthorizedRoles.class);
            if (methodAnnotations != null && methodAnnotations.length > 0) {
                AuthorizedRoles methodAnnotation = methodAnnotations[0];

                String[] methodRoles = methodAnnotation.value();
                for (String methodRole : methodRoles) {
                    if (sessionContext.isCallerInRole(methodRole)) {
                        userHasRequiredRoleAssigned = true;
                        break;
                    }
                }
            }
        }

        Object returnValue;

        if (userHasRequiredRoleAssigned == true) {
            logger.debug("User " + sessionContext.getCallerPrincipal().getName() + " may call method "
                    + declaringClass.getCanonicalName() + "#" + method.getName());
            returnValue = ctx.proceed();
        } else {
            logger.debug("PERMISSION DENIED! User " + sessionContext.getCallerPrincipal().getName()
                    + " may not call method " + declaringClass.getCanonicalName() + "#" + method.getName());

            throw new UserNotAuthorizedException();
        }

        return returnValue;
    }
}
