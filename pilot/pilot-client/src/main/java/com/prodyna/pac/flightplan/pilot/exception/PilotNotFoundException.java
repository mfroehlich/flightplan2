/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.exception;

import com.prodyna.pac.flightplan.common.exception.TechnicalException;
import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotNotFoundException extends TechnicalException {

    private static final long serialVersionUID = -7098005404014021307L;

    public PilotNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
