/**
 * 
 */
package com.prodyna.pac.flightplan.user.exception;

import java.util.List;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class UserValidationException extends FunctionalException {

    private static final long serialVersionUID = 5402459996621409192L;

    /**
     * @param message
     * @param errorCode
     */
    public UserValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    /**
     * @param message
     * @param errorCode
     */
    public UserValidationException(String message, List<ErrorCode> errorCodes) {
        super(message, errorCodes);
    }
}
