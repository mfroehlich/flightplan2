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
public class AircraftTypeValidationException extends FunctionalException {

    private static final long serialVersionUID = -1941100953004284799L;

    /**
     * @param message
     * @param errorCode
     */
    public AircraftTypeValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    /**
     * @param message
     * @param errorCode
     */
    public AircraftTypeValidationException(String message, Collection<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
