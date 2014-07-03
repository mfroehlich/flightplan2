/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract exception class to group all exceptions that result from functional validation and that can be corrected by
 * the user.
 * 
 * @author mfroehlich
 *
 */
public abstract class FunctionalException extends Exception {

    private static final long serialVersionUID = 5100854786331031825L;

    private final List<ErrorCode> errorCodes;

    public FunctionalException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCodes = new ArrayList<ErrorCode>();
        this.errorCodes.add(errorCode);
    }

    /**
     * 
     * @param message
     * @param errorCode
     */
    public FunctionalException(String message, List<ErrorCode> errorCodes) {
        super(message);

        this.errorCodes = errorCodes;
    }

    /**
     * 
     * Get the list of error codes of the errors encapsulated by this exception.
     * 
     * @return
     */
    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
