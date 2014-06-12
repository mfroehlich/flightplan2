/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 5100854786331031825L;

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
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
