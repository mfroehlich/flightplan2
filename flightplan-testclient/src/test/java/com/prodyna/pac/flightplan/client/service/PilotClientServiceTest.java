/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;

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
        Pilot createPilot = service.createPilot(pilot);

        Assert.assertNotNull(pilot.getId());

        pilot.setId(createPilot.getId());
        Assert.assertEquals(pilot, createPilot);
    }
}
