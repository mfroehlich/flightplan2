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
public class UserNotAuthorizedException extends TechnicalException {

    private static final long serialVersionUID = -2996171187673912379L;

    /**
     * @param message
     * @param errorCode
     */
    public UserNotAuthorizedException() {
        super("User has not assigned the required roles.", ApplicationErrorCode.USER_NOT_AUTHORIZED);
    }
}
