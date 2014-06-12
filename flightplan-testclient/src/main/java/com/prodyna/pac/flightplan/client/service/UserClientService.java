/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.service.PilotService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class UserClientService extends AbstractClientService {

    private final PilotService pilotService;

    public UserClientService() {
        pilotService = createRestService(PilotService.class);
    }

    public Pilot loadUserByUserName(String userName) {
        return pilotService.loadPilotByUserName(userName);
    }
}
