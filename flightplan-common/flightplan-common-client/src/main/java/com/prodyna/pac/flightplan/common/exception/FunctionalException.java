/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class FunctionalException extends Exception {

    private static final long serialVersionUID = 5100854786331031825L;

    private final Collection<ErrorCode> errorCodes;

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
    public FunctionalException(String message, Collection<ErrorCode> errorCodes) {
        super(message);

        this.errorCodes = errorCodes;
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Collection<ErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
