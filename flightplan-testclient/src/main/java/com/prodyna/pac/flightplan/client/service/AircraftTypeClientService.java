/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.service.AircraftTypeService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeClientService extends AbstractClientService {

    private static final Logger logger = LoggerFactory.getLogger(AircraftTypeClientService.class);

    private final AircraftTypeService aircraftTypeService;

    public AircraftTypeClientService() {
        aircraftTypeService = createRestService(AircraftTypeService.class);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param type
     * @return
     */
    public AircraftType createAircraftType(AircraftType type) {
        type.setId(UUID.randomUUID().toString());
        AircraftType createdType = aircraftTypeService.createAircraftType(type);
        return createdType;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param aircraftTypeId
     * @return
     */
    public AircraftType loadAircraftTypeById(String aircraftTypeId) {
        logger.debug("Calling REST-Service to load AircraftType by id '" + aircraftTypeId + "'");
        AircraftType type = aircraftTypeService.loadAircraftTypeById(aircraftTypeId);
        return type;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public List<AircraftType> loadAllAircraftTypes() {
        List<AircraftType> types = aircraftTypeService.loadAllAircraftTypes();
        logger.debug("Found the following AircraftTypes in the database: " + types.size());
        types.forEach(t -> logger.debug("   " + t));
        return types;

    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param type
     * @return
     */
    public AircraftType updateAircraftType(AircraftType type) {
        logger.debug("Calling REST-Service to update AircraftType: " + type);
        AircraftType updatedType = aircraftTypeService.updateAircraftType(type);
        return updatedType;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param aircraftTypeId
     */
    public void deleteAircraftTypeById(String aircraftTypeId) {
        logger.debug("Calling REST-Service to delete AircraftType by id '" + aircraftTypeId + "'");
        aircraftTypeService.deleteAircraftTypeById(aircraftTypeId);
    }
}
