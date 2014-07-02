/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.exception;

import java.util.Collection;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotValidationException extends FunctionalException {

    private static final long serialVersionUID = 623998965170440819L;

    public PilotValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PilotValidationException(String message, Collection<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
