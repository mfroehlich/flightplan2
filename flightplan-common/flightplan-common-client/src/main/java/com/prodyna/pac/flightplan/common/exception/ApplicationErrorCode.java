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
public class ApplicationErrorCode extends ErrorCode {

    public static final ErrorCode USER_NOT_LOGGED_IN = new ApplicationErrorCode("user_not_logged_in");
    public static final ErrorCode USER_NOT_AUTHORIZED = new ApplicationErrorCode("user_not_authorized");

    public ApplicationErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "global";
    }
}
