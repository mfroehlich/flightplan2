/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.exception;

import com.prodyna.pac.flightplan.common.exception.FunctionalException;
import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotPersistenceException extends FunctionalException {

    private static final long serialVersionUID = -9079480752074384290L;

    public PilotPersistenceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
