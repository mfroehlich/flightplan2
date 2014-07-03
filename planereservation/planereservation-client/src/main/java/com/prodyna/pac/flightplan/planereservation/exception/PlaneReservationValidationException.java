/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.exception;

import java.util.List;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneReservationValidationException extends FunctionalException {

    private static final long serialVersionUID = 1674368653573656881L;

    /**
     * @param message
     * @param errorCode
     */
    public PlaneReservationValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    /**
     * @param message
     * @param errorCodes
     */
    public PlaneReservationValidationException(String message, List<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
