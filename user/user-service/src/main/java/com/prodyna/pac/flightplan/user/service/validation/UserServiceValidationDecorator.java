/**
 * 
 */
package com.prodyna.pac.flightplan.user.service.validation;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
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

    @Inject
    private ErrorCodeCollector<User> collector;

    @Override
    public User createUser(User user) throws UserValidationException {
        executeBeanValidationOnUser(user);
        checkUserNameUnique(user);
        return delegate.createUser(user);
    }

    @Override
    public String loadUserIdByUserName(String userName) {
        return delegate.loadUserIdByUserName(userName);
    }

    @Override
    public User loadUserById(String userId) {
        return delegate.loadUserById(userId);
    }

    @Override
    public User updateUser(User user) throws UserValidationException {
        executeBeanValidationOnUser(user);
        checkUserNameUnique(user);
        return delegate.updateUser(user);
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

    /**
     * TODO mfroehlich Comment me
     * 
     * @param user
     * @throws UserValidationException
     */
    private void checkUserNameUnique(User user) throws UserValidationException {
        boolean userNameUnique = delegate.isUserNameUnique(user);
        if (userNameUnique == false) {
            throw new UserValidationException("Username is not unique.", UserErrorCode.USERNAME_MUST_BE_UNIQUE);
        }
    }

    /**
     * Validate the {@link User} via bean validation and translate the constraint violations into according error codes.
     * 
     * @param user
     * @throws UserValidationException
     */
    private void executeBeanValidationOnUser(User user) throws UserValidationException {

        collector.validateProperty(user, User.PROP_ID, UserErrorCode.ID_MUST_NOT_BE_NULL);
        collector.validateProperty(user, User.PROP_USERNAME, UserErrorCode.USERNAME_INVALID);
        collector.validateProperty(user, User.PROP_FIRSTNAME, UserErrorCode.FIRSTNAME_INVALID);
        collector.validateProperty(user, User.PROP_LASTNAME, UserErrorCode.LASTNAME_INVALID);
        collector.validateProperty(user, User.PROP_PASSWORD, UserErrorCode.PASSWORD_INVALID);
        collector.validateProperty(user, User.PROP_EMAIL, UserErrorCode.EMAIL_INVALID);

        if (collector.hasErrorCodes()) {
            throw new UserValidationException("Found validation errors.", collector.getErrorCodes());
        }
    }

    @Override
    public boolean isUserNameUnique(User user) {
        // TODO Auto-generated method stub
        return false;
    }
}
