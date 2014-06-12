/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.service.AircraftTypeService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class AircraftTypeServiceValidationDecorator implements AircraftTypeService {

    @Inject
    @Delegate
    private AircraftTypeService delegate;

    @Override
    public AircraftType createAircraftType(AircraftType aircraftType) {
        return delegate.createAircraftType(aircraftType);
    }

    @Override
    public AircraftType loadAircraftTypeById(String id) {
        return delegate.loadAircraftTypeById(id);
    }

    @Override
    public List<AircraftType> loadAllAircraftTypes() {
        return delegate.loadAllAircraftTypes();
    }

    @Override
    public AircraftType updateAircraftType(AircraftType aircraftType) {
        return delegate.updateAircraftType(aircraftType);
    }

    @Override
    public void deleteAircraftTypeById(String aircraftTypeId) {

        checkAircraftTypeNotReferencedByPlane(aircraftTypeId);

        delegate.deleteAircraftTypeById(aircraftTypeId);
    }

    /**
     * Checks that the {@link AircraftType} to be deleted is no more referenced by any {@link Plane}.
     * 
     * @param aircraftTypeId
     */
    private void checkAircraftTypeNotReferencedByPlane(String aircraftTypeId) {
        // TODO mfroehlich implement me
    }
}
