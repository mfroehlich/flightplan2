/**
 * 
 */
package com.prodyna.pac.flightplan.user.service.validation;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.user.service.UserService;

/**
 * TODO mfroehlich Comment me
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
    public String encryptPassword(String password) {
        return delegate.encryptPassword(password);
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        delegate.updatePassword(userId, oldPassword, newPassword);
    }

}
