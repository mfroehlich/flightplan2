/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import java.util.List;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;

/**
 * Exception type to handle validation errors when validating {@link Reservation} objects.
 * 
 * @author mfroehlich
 *
 */
public class ReservationValidationException extends FunctionalException {

    private static final long serialVersionUID = 4233687488973171415L;

    public ReservationValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ReservationValidationException(String message, List<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
