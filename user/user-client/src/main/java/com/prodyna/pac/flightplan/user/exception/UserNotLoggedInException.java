/**
 * 
 */
package com.prodyna.pac.flightplan.user.exception;

import com.prodyna.pac.flightplan.common.exception.TechnicalException;

/**
 * Exception for: user is not logged in.
 * 
 * @author mfroehlich
 *
 */
public class UserNotLoggedInException extends TechnicalException {

    private static final long serialVersionUID = -6603308142963671887L;

    public UserNotLoggedInException() {
        super("User is not logged in.", UserErrorCode.USER_NOT_LOGGED_IN);
    }
}
