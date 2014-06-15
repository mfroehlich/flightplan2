/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotErrorCode;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.pilot.service.PilotService;
import com.prodyna.pac.flightplan.utils.StringUtils;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PilotServiceValidationDecorator implements PilotService {

    @Inject
    @Delegate
    private PilotService delegate;

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     */
    @Override
    public Pilot createPilot(Pilot pilot) {

        checkPilotId(pilot.getId());
        checkPilotUserName(pilot);

        return delegate.createPilot(pilot);
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     */
    @Override
    public Pilot updatePilot(Pilot pilot) {

        checkPilotId(pilot.getId());

        return delegate.updatePilot(pilot);
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     */
    @Override
    public void deletePilotById(String pilotId) {

        checkPilotId(pilotId);

        delegate.deletePilotById(pilotId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     */
    private void checkPilotId(String pilotId) {
        if (StringUtils.trim(pilotId, null) == null) {
            throw new PilotValidationException("Pilot id not set properly: '" + pilotId + "'",
                    PilotErrorCode.PILOT_ID_NOT_SET);
        }
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     */
    private void checkPilotUserName(Pilot pilot) {
        // TODO mfroehlich irgendwie prüfen, ob der UserName des Piloten eindeutig ist!
        // TODO mfroehlich ACHTUNG! Diese Prüfung muss auch im Cluster funktionieren! nur wie!?
    }

    @Override
    public Pilot loadPilotById(String id) {
        return delegate.loadPilotById(id);
    }

    @Override
    public List<Pilot> loadAllPilots() {
        return delegate.loadAllPilots();
    }
}
