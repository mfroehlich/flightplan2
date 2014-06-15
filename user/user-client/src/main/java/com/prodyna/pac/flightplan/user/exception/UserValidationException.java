/**
 * 
 */
package com.prodyna.pac.flightplan.user.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.TechnicalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class UserValidationException extends TechnicalException {

    private static final long serialVersionUID = 5402459996621409192L;

    /**
     * @param message
     * @param errorCode
     */
    public UserValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
