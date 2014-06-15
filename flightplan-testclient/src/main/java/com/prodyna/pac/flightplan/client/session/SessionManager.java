/**
 * 
 */
package com.prodyna.pac.flightplan.client.session;

import org.controlsfx.dialog.Dialogs.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.service.UserClientService;
import com.prodyna.pac.flightplan.user.exception.UserNotLoggedInException;

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
    private String userId;

    private SessionManager() {
    }

    public static final SessionManager getInstance() {
        return instance;
    }

    public void loginUser(UserInfo userInfo) {
        this.userInfo = userInfo;
        String userName = userInfo.getUserName();
        try {
            UserClientService userClientService = new UserClientService();
            userId = userClientService.loadUserIdByUserName(userName);
        } catch (Exception ex) {
            logger.error("Error logging in user " + userName, ex);
            this.userInfo = null;
            this.userId = null;
            throw new UserNotLoggedInException();
        }
    }

    public String logoutUser() {
        String userId = this.userId;
        this.userInfo = null;
        this.userId = null;
        return userId;
    }

    public UserInfo getLoggedUserInfo() {
        return this.userInfo;
    }

    public String getLoggedInUserId() {
        if (userId == null) {
            throw new UserNotLoggedInException();
        }
        return userId;
    }

    public boolean isUserLoggedIn() {
        return this.userId != null;
    }
}
