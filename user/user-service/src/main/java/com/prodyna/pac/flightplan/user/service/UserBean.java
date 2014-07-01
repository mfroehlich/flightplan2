/**
 * 
 */
package com.prodyna.pac.flightplan.user.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.user.entity.User;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * Stateless EJB - Implementation of {@link UserService} providing CRUD service methods for {@link User}.
 * 
 * @author mfroehlich
 *
 */
@Stateless
@MethodCallsMonitored
@Logging
public class UserBean implements UserService {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Override
    public String loadUserIdByUserName(String userName) {
        Query query = em.createNamedQuery(User.QUERY_LOAD_USER_ID_BY_USERNAME);
        query.setParameter("username", userName);
        String userId = (String) query.getSingleResult();
        return userId;
    }

    @Override
    public User loadUserById(String userId) {
        return em.find(User.class, userId);
    }

    @Override
    public String encryptPassword(String password) throws UserValidationException {

        String encryptedPassword;
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = password.getBytes("UTF-8");
            byte[] digestedPasswordBytes = digester.digest(passwordBytes);
            byte[] encodedPassword = BASE64EncoderStream.encode(digestedPasswordBytes);
            encryptedPassword = new String(encodedPassword);

            logger.debug("Password encryption: " + password + " -> " + encryptedPassword);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("Error encoding password.", e);
            encryptedPassword = null;
        }

        return encryptedPassword;
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) throws UserValidationException {

        String oldPwdEnc = encryptPassword(oldPassword);
        String newPwdEnc = encryptPassword(newPassword);

        Query query = em.createNamedQuery(User.QUERY_UPDATE_USER_PASSWORD);
        query.setParameter("newPwd", newPwdEnc);
        query.setParameter("oldPwd", oldPwdEnc);
        query.setParameter("userId", userId);
        int numberOfChangedRows = query.executeUpdate();

        if (numberOfChangedRows == 0) {
            // TODO mfroehlich Richtige Exception werfen
            throw new UserValidationException("Old password is not correct.",
                    UserErrorCode.PASSWORD_UPDATE_OLD_PASSWORD_IS_NOT_CORRECT);
        }
    }
}
