/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import java.util.Collection;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;

/**
 * FunctionalException for (validation) errors concerning the lifecycle of a {@link Reservation} object.
 * 
 * @author mfroehlich
 *
 */
public class ReservationWorkflowException extends FunctionalException {

    private static final long serialVersionUID = -6077013051170080240L;

    public ReservationWorkflowException(String message, Collection<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
