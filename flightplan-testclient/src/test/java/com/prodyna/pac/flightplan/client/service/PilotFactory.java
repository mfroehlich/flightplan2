/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.util.Date;
import java.util.HashSet;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotFactory {

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public static Pilot createPilot() {

        Pilot pilot = new Pilot();
        pilot.setUserName("testuser");
        pilot.setFirstName("test");
        pilot.setLastName("test");
        pilot.setId(null);
        pilot.setLicenceValidity(new Date());
        pilot.seteMailAddress("test@test.de");
        pilot.setPassword("test");
        pilot.setAssignedAircraftTypes(new HashSet<>());

        return pilot;
    }

}
