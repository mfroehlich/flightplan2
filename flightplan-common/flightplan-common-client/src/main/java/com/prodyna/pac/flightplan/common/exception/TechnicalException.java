/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

/**
 * Abstract exception class to group all exceptions that have a technical reason which cannot be corrected by the user.
 * 
 * @author mfroehlich
 *
 */
public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = -9134879872238960479L;

    private final ErrorCode errorCode;

    /**
     * 
     * @param message
     * @param errorCode
     */
    public TechnicalException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCode = errorCode;
    }

    /**
     * 
     * Get the error code of the error encapsulated by this exception.
     * 
     * @return
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
