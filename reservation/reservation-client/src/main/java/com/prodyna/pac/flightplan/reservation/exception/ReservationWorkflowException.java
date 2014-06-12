/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.TechnicalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationWorkflowException extends TechnicalException {

    private static final long serialVersionUID = -6077013051170080240L;

    public ReservationWorkflowException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
