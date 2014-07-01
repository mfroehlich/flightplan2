/**
 * 
 */
package com.prodyna.pac.flightplan.user.service.validation;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.user.entity.User;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;
import com.prodyna.pac.flightplan.user.service.UserService;
import com.prodyna.pac.flightplan.utils.StringUtils;

/**
 * {@link Decorator} providing validation logic to be executed before {@link UserService} methods are called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class UserServiceValidationDecorator implements UserService {

    @Delegate
    @Inject
    private UserService delegate;

    @Override
    public String loadUserIdByUserName(String userName) {
        return delegate.loadUserIdByUserName(userName);
    }

    @Override
    public User loadUserById(String userId) {
        return delegate.loadUserById(userId);
    }

    @Override
    public String encryptPassword(String password) throws UserValidationException {
        checkPassword(password);
        return delegate.encryptPassword(password);
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) throws UserValidationException {
        checkPassword(oldPassword);
        checkPassword(newPassword);

        if (oldPassword.equals(newPassword)) {
            throw new UserValidationException("New password is equal to old password.",
                    UserErrorCode.NEW_PASSWORD_MAY_NOT_BE_EQUAL_TO_OLD_PASSWORD);
        }

        delegate.updatePassword(userId, oldPassword, newPassword);
    }

    /**
     * Checks if the specified password is null or empty.
     * 
     * @param oldPassword
     * @throws UserValidationException
     */
    private void checkPassword(String oldPassword) throws UserValidationException {
        if (StringUtils.trim(oldPassword, null) == null) {
            throw new UserValidationException("Password '" + oldPassword + "' is no valid value.",
                    UserErrorCode.PASSWORD_INVALID_VALUE);
        }
    }
}
