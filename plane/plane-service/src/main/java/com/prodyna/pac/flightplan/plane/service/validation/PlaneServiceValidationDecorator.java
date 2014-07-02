/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneErrorCode;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;
import com.prodyna.pac.flightplan.plane.service.PlaneService;

/**
 * {@link Decorator} providing validation logic to be executed before {@link PlaneService} methods are called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PlaneServiceValidationDecorator implements PlaneService {

    @Inject
    private ErrorCodeCollector<Plane> collector;

    @Inject
    private Logger logger;

    @Delegate
    @Inject
    private PlaneService delegate;

    @Override
    public Plane createPlane(Plane plane) throws PlaneValidationException {
        executeBeanValidationOnPlane(plane);
        checkPlaneNameAndNumberPlateNotUnique(plane);
        return delegate.createPlane(plane);
    }

    @Override
    public Plane loadPlaneById(String planeId) {
        return delegate.loadPlaneById(planeId);
    }

    @Override
    public List<Plane> loadAllPlanes() {
        return delegate.loadAllPlanes();
    }

    @Override
    public Plane updatePlane(Plane plane) throws PlaneValidationException {
        executeBeanValidationOnPlane(plane);
        checkPlaneNameAndNumberPlateNotUnique(plane);
        return delegate.updatePlane(plane);
    }

    @Override
    public void deletePlaneById(String planeId) throws PlaneValidationException {
        Plane plane = loadPlaneById(planeId);
        if (plane == null) {
            throw new PlaneValidationException("Plane with id '" + planeId
                    + "' not found in the database. Aborting delete action.", PlaneErrorCode.PLANE_NOT_FOUND_BY_ID);
        }

        try {
            delegate.deletePlaneById(planeId);
        } catch (Exception e) {
            logger.error("Error deleting plane '" + planeId + "'.");
            throw new PlaneValidationException("Error deleting plane '" + planeId + "'.",
                    PlaneErrorCode.PLANE_CANNOT_BE_DELETED);
        }

    }

    @Override
    public boolean isPlaneNameAndNumberPlateUnique(Plane plane) {
        return delegate.isPlaneNameAndNumberPlateUnique(plane);
    }

    /**
     * Validate the {@link Plane} via bean validation and translate the constraint violations into according error
     * codes.
     * 
     * @param plane
     * @throws PlaneValidationException
     */
    private void executeBeanValidationOnPlane(Plane plane) throws PlaneValidationException {

        collector.validateProperty(plane, Plane.PROP_ID, PlaneErrorCode.ID_MUST_NOT_BE_NULL);
        collector.validateProperty(plane, Plane.PROP_NAME, PlaneErrorCode.NAME_INVALID);
        collector.validateProperty(plane, Plane.PROP_NUMBERPLATE, PlaneErrorCode.NUMBERPLATE_INVALID);
        collector.validateProperty(plane, Plane.PROP_AIRCRAFTTYPE, PlaneErrorCode.AIRCRAFTTYPE_MUST_NOT_BE_NULL);

        if (collector.hasErrorCodes()) {
            throw new PlaneValidationException("Found validation errors.", collector.getErrorCodes());
        }
    }

    /**
     * Check if the plane's name and number plate are unique each.
     * 
     * @param plane
     * @throws PlaneValidationException
     */
    private void checkPlaneNameAndNumberPlateNotUnique(Plane plane) throws PlaneValidationException {
        boolean planeNameAndNumberPlateUnique = delegate.isPlaneNameAndNumberPlateUnique(plane);
        if (planeNameAndNumberPlateUnique == false) {
            logger.debug("---------------------------- name or numberplate is not unique!!!");
            throw new PlaneValidationException("Plane name or number plate is not unique.",
                    PlaneErrorCode.NAME_AND_NUMBERPLATE_MUST_BE_UNIQUE);
        }
    }
}
