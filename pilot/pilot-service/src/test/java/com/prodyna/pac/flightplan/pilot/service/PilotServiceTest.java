/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotErrorCode;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

@RunWith(Arquillian.class)
public class PilotServiceTest {

    @Inject
    private PilotService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Test
    @InSequence(100)
    public void testCreatePilotSuccess() {

        Pilot pilot = createPilot();

        try {
            Pilot createdPilot = service.createPilot(pilot);
            Assert.assertEquals(pilot.getId(), createdPilot.getId());
            Assert.assertEquals(pilot.getUserName(), createdPilot.getUserName());
            Assert.assertEquals(pilot.getFirstName(), createdPilot.getFirstName());
            Assert.assertEquals(pilot.getLastName(), createdPilot.getLastName());
            Assert.assertEquals(pilot.getPassword(), createdPilot.getPassword());
            Assert.assertEquals(pilot.getLicenceValidity(), createdPilot.getLicenceValidity());
            Assert.assertEquals(pilot.geteMailAddress(), createdPilot.geteMailAddress());
            Assert.assertEquals(pilot.getAssignedAircraftTypes(), createdPilot.getAssignedAircraftTypes());
        } catch (Exception e) {
            Assert.fail("Caught unexpected exception." + e.getMessage());
        }
    }

    @Test
    @InSequence(101)
    public void testCreatePilotIdNull() {

        Pilot pilot = createPilot();
        pilot.setId(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.ID_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(102)
    public void testCreatePilotUserNameInvalid() {

        Pilot pilot = createPilot();
        pilot.setUserName(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.USERNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // too long
        pilot.setUserName("123456789012345678901234567890123456789012345678901");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.USERNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // empty
        pilot.setUserName("");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.USERNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(103)
    public void testCreatePilotFirstNameInvalid() {

        Pilot pilot = createPilot();
        pilot.setFirstName(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.FIRSTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // too long
        pilot.setFirstName("123456789012345678901234567890123456789012345678901");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.FIRSTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // empty
        pilot.setFirstName("");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.FIRSTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(104)
    public void testCreatePilotLastNameInvalid() {

        Pilot pilot = createPilot();
        pilot.setLastName(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.LASTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // too long
        pilot.setLastName("123456789012345678901234567890123456789012345678901");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.LASTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // empty
        pilot.setLastName("");

        try {
            service.createPilot(pilot);
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.LASTNAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(104)
    public void testCreatePilotEmailInvalid() {

        Pilot pilot = createPilot();
        pilot.seteMailAddress(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // too long
        pilot.seteMailAddress("123456789012345678901234567890123456789012345678901");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // empty
        pilot.seteMailAddress("");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        pilot.seteMailAddress("test");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        pilot.seteMailAddress("test@test");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        pilot.seteMailAddress("test@test.de");

        try {
            service.createPilot(pilot);
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.EMAIL_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(105)
    public void testCreatePilotPasswordInvalid() {

        Pilot pilot = createPilot();
        pilot.setPassword(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.PASSWORD_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // too long
        pilot.setPassword("123456789012345678901234567890123456789012345678901");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.PASSWORD_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }

        // empty
        pilot.setPassword("");

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.PASSWORD_INVALID));
        } catch (Exception ex) {
            Assert.fail("UserValidationException expected");
        }
    }

    @Test
    @InSequence(106)
    public void testCreatePilotLicenceValidityInvalid() {

        Pilot pilot = createPilot();
        pilot.setLicenceValidity(null);

        try {
            service.createPilot(pilot);
            Assert.fail("Creating invalid pilot should result in an exception.");
        } catch (PilotValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PilotErrorCode.LICENCE_VALIDITY_INVALID));
        } catch (UserValidationException e) {
            Assert.fail("Caught unexpected exception.");
        } catch (Exception ex) {
            Assert.fail("PilotValidationException expected");
        }

        pilot.setLicenceValidity(new Date());

        try {
            Pilot createdPilot = service.createPilot(pilot);
            Assert.assertEquals(pilot.getLicenceValidity(), createdPilot.getLicenceValidity());
        } catch (Exception e) {
            Assert.fail("Caught unexpected exception." + e);
        }
    }

    public void testCreatePilotUserNameUnique() {
        Pilot pilot = createPilot();

        // Username already exists
        pilot.setUserName("mfroehlich");

        try {
            service.createPilot(pilot);
            Assert.fail("Exception expected when persisting invalid user.");
        } catch (PilotValidationException e) {
            e.printStackTrace();
        } catch (UserValidationException e) {
        }
    }

    @Test
    @InSequence(200)
    public void testLoadPilotById() {
        Pilot loadedPilot;
        try {
            loadedPilot = service.loadPilotById("1");
            Assert.assertNotNull(loadedPilot);
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }

        try {
            service.loadPilotById(null);
        } catch (PilotNotFoundException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PilotErrorCode.ID_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("Unexpected exception.");
        }

        try {
            Pilot notExistingPilot = service.loadPilotById("not_existing_id");
            Assert.assertNull(notExistingPilot);
        } catch (Exception ex) {
            Assert.fail("Unexpected exception.");
        }
    }

    @Test
    @InSequence(300)
    public void testLoadAllPilots() {
        List<Pilot> loadAllPilots = service.loadAllPilots();
        Assert.assertEquals(1, loadAllPilots.size());

        try {
            Pilot p = createPilot();
            service.createPilot(p);
        } catch (Exception e) {
            Assert.fail("Unexcepted Exception.");
        }

        loadAllPilots = service.loadAllPilots();
        Assert.assertEquals(2, loadAllPilots.size());
    }

    @Test
    @InSequence(400)
    public void testUpdatePilotSuccess() {

        try {
            Pilot pilot = service.loadPilotById("1");
            pilot.setFirstName("test");
            pilot.setLastName("test");
            pilot.setUserName("test");
            Pilot updatedPilot = service.updatePilot(pilot);

            Assert.assertEquals("test", updatedPilot.getFirstName());
            Assert.assertEquals("test", updatedPilot.getLastName());
            Assert.assertEquals("test", updatedPilot.getUserName());

        } catch (Exception e) {
            Assert.fail("unexpected exception.");
        }
    }

    @Test
    @InSequence(401)
    public void testUpdatePilotUserNameCollision() {

        try {
            Pilot pilot = createPilot();
            Pilot createdPilot = service.createPilot(pilot);

            // already exists
            createdPilot.setUserName("mfroehlich");
            service.updatePilot(createdPilot);
            Assert.fail("Exception expected!");
        } catch (UserValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(UserErrorCode.USERNAME_MUST_BE_UNIQUE));
        } catch (Exception ex) {
            Assert.fail("Unexpected exception!");
        }
    }

    @Test
    @InSequence(500)
    public void testDeletePilotByIdInvalid() {
        try {
            service.deletePilotById(null);
            Assert.fail("Exception expected.");
        } catch (PilotNotFoundException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PilotErrorCode.ID_MUST_NOT_BE_NULL));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }

        try {
            service.deletePilotById("");
            Assert.fail("Exception expected.");
        } catch (PilotValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PilotErrorCode.PILOT_CANNOT_BE_DELETED));
        } catch (Exception e) {
            Assert.fail("Unexpected exception." + e);
        }
    }

    @Test
    @InSequence(501)
    public void testDeletePilotByIdReferencedPilotError() {

        List<Pilot> loadAllPilots = service.loadAllPilots();
        Assert.assertEquals(1, loadAllPilots.size());

        try {
            service.deletePilotById("1");
            Assert.fail("Exception expected.");
        } catch (PilotValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PilotErrorCode.PILOT_CANNOT_BE_DELETED));
        } catch (Exception e) {
            Assert.fail("Unexcepted exception.");
        }

        loadAllPilots = service.loadAllPilots();
        Assert.assertEquals(0, loadAllPilots.size());
    }

    @Test
    @InSequence(502)
    public void testDeletePilotByIdSuccess() {

        try {
            Pilot pilot = createPilot();
            service.createPilot(pilot);
            service.deletePilotById(pilot.getId());
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    private Pilot createPilot() {
        Pilot pilot = new Pilot();
        pilot.setId("2");
        pilot.setUserName("testuser");
        pilot.setFirstName("testfirstname");
        pilot.setLastName("testlastname");
        pilot.setPassword("testpassword");
        pilot.seteMailAddress("testemail@test.de");
        pilot.setLicenceValidity(new Date());

        List<AircraftType> types = new ArrayList<AircraftType>();
        AircraftType type = new AircraftType();
        type.setId("2");
        types.add(type);
        pilot.setAssignedAircraftTypes(types);
        return pilot;
    }
}
