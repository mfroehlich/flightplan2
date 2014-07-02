/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeErrorCode;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;
import com.prodyna.pac.flightplan.plane.service.AircraftTypeService;

/**
 * {@link Decorator} providing validation logic to be executed before {@link AircraftTypeService} methods are called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class AircraftTypeServiceValidationDecorator implements AircraftTypeService {

    @Inject
    private ErrorCodeCollector<AircraftType> collector;

    @Inject
    @Delegate
    private AircraftTypeService delegate;

    @Override
    public AircraftType createAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException {
        executeBeanValidationOnAircraftType(aircraftType);
        checkDescriptionUnique(aircraftType);
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
    public AircraftType updateAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException {
        executeBeanValidationOnAircraftType(aircraftType);
        checkDescriptionUnique(aircraftType);
        return delegate.updateAircraftType(aircraftType);
    }

    @Override
    public void deleteAircraftTypeById(String aircraftTypeId) throws AircraftTypeValidationException {
        AircraftType type = delegate.loadAircraftTypeById(aircraftTypeId);
        if (type == null) {
            throw new AircraftTypeValidationException("AircraftType to be deleted not existent.",
                    AircraftTypeErrorCode.AIRCRAFTTYPE_TO_BE_DELETED_NOT_FOUND);
        }

        checkAircraftTypeReferencedByPlane(aircraftTypeId);
        delegate.deleteAircraftTypeById(aircraftTypeId);
    }

    @Override
    public boolean isAircraftTypeReferencedByPlanes(String aircraftTypeId) {
        return delegate.isAircraftTypeReferencedByPlanes(aircraftTypeId);
    }

    @Override
    public boolean isAircraftTypeDescriptionUnique(AircraftType aircraftType) {
        return delegate.isAircraftTypeDescriptionUnique(aircraftType);
    }

    /**
     * Check that the {@link AircraftType} to be deleted is no more referenced by any {@link Plane}.
     * 
     * @param aircraftTypeId
     * @throws AircraftTypeValidationException
     */
    private void checkAircraftTypeReferencedByPlane(String aircraftTypeId) throws AircraftTypeValidationException {
        boolean aircraftTypeReferenced = delegate.isAircraftTypeReferencedByPlanes(aircraftTypeId);
        if (aircraftTypeReferenced == true) {
            throw new AircraftTypeValidationException("AircraftType " + aircraftTypeId
                    + " must not be deleted as it is referenced by planes.",
                    AircraftTypeErrorCode.AIRCRAFTTYPE_REFERENCED_BY_PLANES);
        }
    }

    /**
     * Check if the description of the specified {@link AircraftType} is unique in the database.
     * 
     * @param aircraftType
     * @throws AircraftTypeValidationException
     */
    private void checkDescriptionUnique(AircraftType aircraftType) throws AircraftTypeValidationException {
        boolean descriptionUnique = delegate.isAircraftTypeDescriptionUnique(aircraftType);
        if (descriptionUnique == false) {
            throw new AircraftTypeValidationException("AircraftType" + aircraftType.getId()
                    + " does not define a unique description.", AircraftTypeErrorCode.DESCRIPTION_NOT_UNIQUE);
        }
    }

    /**
     * Validate the {@link AircraftType} via bean validation and translate the constraint violations into according
     * error codes.
     * 
     * @param aircraftType
     * @throws AircraftTypeValidationException
     */
    private void executeBeanValidationOnAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException {

        collector.validateProperty(aircraftType, AircraftType.PROP_ID, AircraftTypeErrorCode.ID_NOT_SET);
        collector.validateProperty(aircraftType, AircraftType.PROP_DESCRIPTION,
                AircraftTypeErrorCode.DESCRIPTION_IS_NOT_SET);
        collector.validateProperty(aircraftType, AircraftType.PROP_ID, AircraftTypeErrorCode.ID_NOT_SET);

        if (collector.hasErrorCodes()) {
            throw new AircraftTypeValidationException("Found validation errors.", collector.getErrorCodes());
        }
    }
}
