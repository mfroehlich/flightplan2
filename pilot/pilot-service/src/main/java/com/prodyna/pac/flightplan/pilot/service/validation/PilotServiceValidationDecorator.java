/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotErrorCode;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.pilot.service.PilotService;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * {@link Decorator} providing validation logic to be executed before {@link PilotService} methods are called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PilotServiceValidationDecorator implements PilotService {

    @Inject
    @Delegate
    private PilotService delegate;

    @Inject
    private Logger logger;

    @Inject
    private ErrorCodeCollector<Pilot> collector;

    @Override
    public Pilot createPilot(Pilot pilot) throws PilotValidationException, UserValidationException {
        executeBeanValidationOnPilot(pilot);
        return delegate.createPilot(pilot);
    }

    @Override
    public Pilot updatePilot(Pilot pilot) throws PilotValidationException, UserValidationException {
        executeBeanValidationOnPilot(pilot);
        return delegate.updatePilot(pilot);
    }

    @Override
    public Pilot loadPilotById(String id) throws PilotNotFoundException {
        if (id == null) {
            throw new PilotNotFoundException("Id must not be null", PilotErrorCode.ID_MUST_NOT_BE_NULL);
        }
        return delegate.loadPilotById(id);
    }

    @Override
    public List<Pilot> loadAllPilots() {
        return delegate.loadAllPilots();
    }

    @Override
    public void deletePilotById(String pilotId) throws PilotValidationException, PilotNotFoundException {
        if (pilotId == null) {
            throw new PilotNotFoundException("Id must not be null", PilotErrorCode.ID_MUST_NOT_BE_NULL);
        }

        try {
            delegate.deletePilotById(pilotId);
        } catch (Exception e) {
            logger.debug("Error deleting pilot " + pilotId + ": " + e);
            throw new PilotValidationException("Error deleting pilot '" + pilotId + "'.",
                    PilotErrorCode.PILOT_CANNOT_BE_DELETED);
        }
    }

    /**
     * Validate the {@link Pilot} via bean validation and translate the constraint violations into according error
     * codes.
     * 
     * @param user
     * @throws PilotValidationException
     */
    private void executeBeanValidationOnPilot(Pilot user) throws PilotValidationException {

        collector.validateProperty(user, Pilot.PROP_LICENCE_VALIDITY, PilotErrorCode.LICENCE_VALIDITY_INVALID);
        collector.validateProperty(user, Pilot.PROP_AIRCRAFTTYPES, PilotErrorCode.AIRCRAFTTYPES_MUST_NOT_BE_NULL);

        if (collector.hasErrorCodes()) {
            throw new PilotValidationException("Found validation errors.", collector.getErrorCodes());
        }
    }
}
