/**
 * 
 */
package com.prodyna.pac.flightplan.user.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class UserErrorCode extends ErrorCode {

    public static final ErrorCode USER_NOT_LOGGED_IN = new UserErrorCode("user_not_logged_in");
    public static final ErrorCode USER_NOT_AUTHORIZED = new UserErrorCode("user_not_authorized");
    public static final ErrorCode PASSWORD_UPDATE_NEW_PASSWORDS_ARE_NOT_EQUAL = new UserErrorCode(
            "password_update_new_passwords_are_not_equal");
    public static final ErrorCode PASSWORD_UPDATE_OLD_PASSWORD_IS_NOT_CORRECT = new UserErrorCode(
            "password_update_old_password_is_not_correct");

    public UserErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "global";
    }
}
