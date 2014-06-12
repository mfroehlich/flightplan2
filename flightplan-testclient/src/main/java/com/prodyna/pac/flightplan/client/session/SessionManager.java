/**
 * 
 */
package com.prodyna.pac.flightplan.client.session;

import org.controlsfx.dialog.Dialogs.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.service.UserClientService;
import com.prodyna.pac.flightplan.common.entity.User;
import com.prodyna.pac.flightplan.common.exception.UserNotLoggedInException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private static final SessionManager instance = new SessionManager();

    private UserInfo userInfo;
    private User user;

    private SessionManager() {
    }

    public static final SessionManager getInstance() {
        return instance;
    }

    public void loginUser(UserInfo userInfo) {
        this.userInfo = userInfo;
        try {
            UserClientService userClientService = new UserClientService();
            user = userClientService.loadUserByUserName(userInfo.getUserName());
        } catch (Exception ex) {
            logger.error("Error logging in user " + userInfo.getUserName(), ex);
            this.userInfo = null;
            this.user = null;
            throw new UserNotLoggedInException();
        }
    }

    public User logoutUser() {
        User user = this.user;
        this.userInfo = null;
        this.user = null;
        return user;
    }

    public UserInfo getLoggedUserInfo() {
        return this.userInfo;
    }

    public User getLoggedInUser() {
        if (user == null) {
            throw new UserNotLoggedInException();
        }
        return user;
    }

    public boolean isUserLoggedIn() {
        return this.user != null;
    }
}
