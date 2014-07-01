/**
 * 
 */
package com.prodyna.pac.flightplan.client.changepassword;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.client.service.UserClientService;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;
import com.prodyna.pac.flightplan.utils.StringUtils;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ChangePasswordViewModel {

    private final UserClientService service;

    private final StringProperty userId;

    private final StringProperty oldPwd;

    private final StringProperty newPwd1;

    private final StringProperty newPwd2;

    public ChangePasswordViewModel() {
        this.service = new UserClientService();
        this.userId = new SimpleStringProperty();
        this.oldPwd = new SimpleStringProperty();
        this.newPwd1 = new SimpleStringProperty();
        this.newPwd2 = new SimpleStringProperty();
    }

    public StringProperty oldPwdProperty() {
        return oldPwd;
    }

    public StringProperty newPwd1Property() {
        return newPwd1;
    }

    public StringProperty newPwd2Property() {
        return newPwd2;
    }

    public StringProperty userIdProperty() {
        return userId;
    }

    public void changePassword() throws UserValidationException {
        String pwd1 = StringUtils.trim(newPwd1.get(), "");
        String pwd2 = StringUtils.trim(newPwd2.get(), "");

        if (pwd1.equals(pwd2)) {
            service.changePassword(userId.get(), oldPwd.get(), pwd1);
        } else {
            throw new UserValidationException("New passwords are not equal!",
                    UserErrorCode.PASSWORD_UPDATE_NEW_PASSWORDS_ARE_NOT_EQUAL);
        }
    }
}
