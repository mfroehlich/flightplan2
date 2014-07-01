/**
 * 
 */
package com.prodyna.pac.flightplan.user.service;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;
import com.prodyna.pac.flightplan.user.entity.User;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * Arquillian test for {@link UserService} methods.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class UserServiceTest {

    @Inject
    private UserService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Test
    @InSequence(10)
    public void testLoadUserIdByUserName() {
        String mfroehlichUserId = service.loadUserIdByUserName("mfroehlich");
        Assert.assertEquals("1", mfroehlichUserId);
    }

    @Test
    @InSequence(20)
    public void testLoadUserById() {
        User user = service.loadUserById("1");
        Assert.assertNotNull(user);
        Assert.assertEquals("Markus", user.getFirstName());
        Assert.assertEquals("Fröhlich", user.getLastName());
        Assert.assertEquals("mfroehlich@prodyna.com", user.geteMailAddress());
        Assert.assertEquals("1", user.getId());
        Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", user.getPassword());
        Assert.assertEquals("mfroehlich", user.getUserName());
    }

    @Test
    @InSequence(30)
    public void testEncrpytPassword() {
        String encryptedPassword;
        try {
            encryptedPassword = service.encryptPassword("testpasswort");
            Assert.assertEquals("L64Ebbt93B1JuW85OnqCaQ==", encryptedPassword);
        } catch (UserValidationException e) {
            Assert.fail("Error encrypting password.");
        }

        try {
            encryptedPassword = service.encryptPassword("testpasswort2");
            Assert.assertEquals("b6R3/TA3NjLCjqX4nt+43w==", encryptedPassword);
        } catch (UserValidationException e) {
            Assert.fail("Error encrypting password.");
        }

        try {
            service.encryptPassword(null);
            Assert.fail("No UserValidationException thrown.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.PASSWORD_INVALID_VALUE));
        } catch (Exception ex) {
            Assert.fail("Did not catch a UserValidationException.");
        }

        try {
            service.encryptPassword("");
            Assert.fail("No UserValidationException thrown.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.PASSWORD_INVALID_VALUE));
        } catch (Exception ex) {
            Assert.fail("Did not catch a UserValidationException.");
        }
    }

    @Test
    @InSequence(40)
    public void testUpdatePassword() {
        User user = service.loadUserById("1");
        Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", user.getPassword());

        try {
            service.updatePassword("1", "test", "testpasswort");
        } catch (UserValidationException e) {
            Assert.fail("Password update failed.");
        }

        User assertUser = service.loadUserById("1");
        Assert.assertEquals("L64Ebbt93B1JuW85OnqCaQ==", assertUser.getPassword());
    }

    @Test
    @InSequence(50)
    public void testUpdatePasswordFail() {
        User user = service.loadUserById("1");
        Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", user.getPassword());

        try {
            service.updatePassword("1", "testpasswort", "testpasswort");
            Assert.fail("Invalid password update succeeded. Test must fail here!");
        } catch (UserValidationException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(UserErrorCode.NEW_PASSWORD_MAY_NOT_BE_EQUAL_TO_OLD_PASSWORD));
        }
    }
}
