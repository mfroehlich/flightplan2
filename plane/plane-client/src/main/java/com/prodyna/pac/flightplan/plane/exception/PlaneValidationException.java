/**
 * 
 */
package com.prodyna.pac.flightplan.plane.exception;

import java.util.Collection;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneValidationException extends FunctionalException {

    private static final long serialVersionUID = -9184935526656276486L;

    /**
     * @param message
     * @param errorCode
     */
    public PlaneValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    /**
     * @param message
     * @param errorCode
     */
    public PlaneValidationException(String message, Collection<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
