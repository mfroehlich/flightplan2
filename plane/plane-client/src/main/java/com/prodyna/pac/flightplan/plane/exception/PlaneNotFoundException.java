/**
 * 
 */
package com.prodyna.pac.flightplan.plane.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.TechnicalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneNotFoundException extends TechnicalException {

    private static final long serialVersionUID = -992794337234366735L;

    /**
     * @param message
     * @param errorCode
     */
    public PlaneNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
