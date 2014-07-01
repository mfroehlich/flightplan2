/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationPersistenceException extends FunctionalException {

    private static final long serialVersionUID = 8659431552668592543L;

    /**
     * @param message
     * @param errorCode
     */
    public ReservationPersistenceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
