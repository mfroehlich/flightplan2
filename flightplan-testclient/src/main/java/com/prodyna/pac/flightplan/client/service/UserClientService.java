/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import com.prodyna.pac.flightplan.user.exception.UserValidationException;
import com.prodyna.pac.flightplan.user.service.UserService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class UserClientService extends AbstractClientService {

    private final UserService userService;

    public UserClientService() {
        userService = createRestService(UserService.class);
    }

    public String loadUserIdByUserName(String userName) {
        String userIdByUserName = userService.loadUserIdByUserName(userName);
        return userIdByUserName;
    }

    public void changePassword(String userId, String oldPwd, String newPwd) throws UserValidationException {
        userService.updatePassword(userId, oldPwd, newPwd);
    }
}
