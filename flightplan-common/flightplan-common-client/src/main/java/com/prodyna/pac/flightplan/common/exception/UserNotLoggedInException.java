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
public class UserNotLoggedInException extends TechnicalException {

    private static final long serialVersionUID = -6603308142963671887L;

    public UserNotLoggedInException() {
        super("User is not logged in.", ApplicationErrorCode.USER_NOT_LOGGED_IN);
    }
}
