/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Ignore
public class PilotClientServiceTest {

    @Test
    public void testCreatePilot() {
        PilotClientService service = new PilotClientService();

        Pilot pilot = PilotFactory.createPilot();
        Pilot createdPilot;
        try {
            createdPilot = service.createPilot(pilot);
        } catch (PilotValidationException e) {
            Assert.fail("Caught not expected exception.");
            createdPilot = null;
        } catch (UserValidationException e) {
            Assert.fail("Caught not expected exception.");
            createdPilot = null;
        }

        Assert.assertNotNull(pilot.getId());

        pilot.setId(createdPilot.getId());
        Assert.assertEquals(pilot, createdPilot);
    }
}
