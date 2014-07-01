/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.pilot.service.PilotService;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotClientService extends AbstractClientService {

    private static final Logger logger = LoggerFactory.getLogger(PilotClientService.class);

    private final PilotService pilotService;

    public PilotClientService() {
        this.pilotService = createRestService(PilotService.class);
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     * @throws PilotValidationException
     * @throws UserValidationException
     */
    public Pilot createPilot(Pilot pilot) throws PilotValidationException, UserValidationException {
        pilot.setId(UUID.randomUUID().toString());
        Pilot savedPilot = pilotService.createPilot(pilot);
        logger.debug("Saved pilot " + savedPilot);
        return pilot;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     * @return
     */
    public Pilot loadPilotById(String pilotId) {
        Pilot loadedPilot = pilotService.loadPilotById(pilotId);
        logger.debug("Loaded Pilot by id " + pilotId + " :" + loadedPilot);
        return loadedPilot;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public List<Pilot> loadAllPilots() {
        List<Pilot> pilots = pilotService.loadAllPilots();
        logger.debug("Found the following Pilots in the database: " + pilots.size());
        pilots.forEach(p -> logger.debug("   " + p));
        return pilots;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     * @throws PilotValidationException
     */
    public Pilot updatePilot(Pilot pilot) throws PilotValidationException {
        Pilot updatedPilot = pilotService.updatePilot(pilot);
        logger.debug("Pilot updated to " + updatedPilot);
        return updatedPilot;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     * @throws PilotValidationException
     * @throws PilotNotFoundException
     */
    public void deletePilotById(String pilotId) throws PilotNotFoundException, PilotValidationException {
        logger.debug("Deleting pilot by id: '" + pilotId + "'");
        pilotService.deletePilotById(pilotId);
    }
}
