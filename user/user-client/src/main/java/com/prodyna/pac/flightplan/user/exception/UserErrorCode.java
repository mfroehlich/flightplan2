/**
 * 
 */
package com.prodyna.pac.flightplan.user.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class UserErrorCode extends ErrorCode {

    public static final ErrorCode USER_NOT_LOGGED_IN = new UserErrorCode("user_not_logged_in");
    public static final ErrorCode USER_NOT_AUTHORIZED = new UserErrorCode("user_not_authorized");
    public static final ErrorCode PASSWORD_UPDATE_NEW_PASSWORDS_ARE_NOT_EQUAL = new UserErrorCode(
            "password_update_new_passwords_are_not_equal");
    public static final ErrorCode PASSWORD_UPDATE_OLD_PASSWORD_IS_NOT_CORRECT = new UserErrorCode(
            "password_update_old_password_is_not_correct");
    public static final ErrorCode PASSWORD_INVALID_VALUE = new UserErrorCode("password_invalid_value");
    public static final ErrorCode NEW_PASSWORD_MAY_NOT_BE_EQUAL_TO_OLD_PASSWORD = new UserErrorCode(
            "new_password_may_not_be_equal_to_old_password");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected UserErrorCode() {
    }

    public UserErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "global";
    }
}
