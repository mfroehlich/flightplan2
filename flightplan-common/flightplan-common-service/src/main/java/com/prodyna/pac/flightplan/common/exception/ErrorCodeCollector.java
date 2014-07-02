/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;

/**
 * Helper class to provide easy bean validating.
 * 
 * @author mfroehlich
 *
 */
public class ErrorCodeCollector<T> {

    @Inject
    private Validator validator;

    @Inject
    private Logger logger;

    private final Collection<ErrorCode> errorCodes;

    public ErrorCodeCollector() {
        errorCodes = new ArrayList<ErrorCode>();
    }

    public void validateProperty(T object, String propertyName, ErrorCode errorCode) {
        Set<ConstraintViolation<T>> violations = validator.validateProperty(object, propertyName);
        if (violations.size() > 0) {
            errorCodes.add(errorCode);

            violations.forEach(violation -> logger.debug("Found validation error for " + propertyName + " in class "
                    + object.getClass().getName() + ": " + violation.getMessage()));
        }
    }

    public Collection<ErrorCode> getErrorCodes() {
        return errorCodes;
    }

    public boolean hasErrorCodes() {
        return errorCodes.isEmpty() == false;
    }
}
