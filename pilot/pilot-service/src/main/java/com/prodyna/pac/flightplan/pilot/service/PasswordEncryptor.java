/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
public class PasswordEncryptor {

    @Inject
    private Logger logger;

    public String encryptPassword(String password) {

        String encryptedPassword;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            // new BASE64EncoderStream();
            encryptedPassword = "";

            logger.debug("Password encryption: " + password + " -> " + encryptedPassword);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("Error encoding password.", e);
            encryptedPassword = null;
        }

        return encryptedPassword;
    }
}
