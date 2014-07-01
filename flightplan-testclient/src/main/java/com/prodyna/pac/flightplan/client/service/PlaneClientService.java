/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;
import com.prodyna.pac.flightplan.plane.service.PlaneService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneClientService extends AbstractClientService {

    private static final Logger logger = LoggerFactory.getLogger(PlaneClientService.class);

    private final PlaneService planeService;

    public PlaneClientService() {
        this.planeService = createRestService(PlaneService.class);
    }

    public Plane createPlane(Plane plane) throws PlaneValidationException {
        logger.debug("Creating plane '" + plane + "'.");
        plane.setId(UUID.randomUUID().toString());
        Plane createdPlane = planeService.createPlane(plane);
        logger.debug("Created plane successfully: '" + createdPlane + "'");
        return createdPlane;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param planeId
     * @return
     */
    public Plane loadPlaneById(String planeId) {
        logger.debug("Loading plane by id '" + planeId + "'.");
        Plane loadedPlane = planeService.loadPlaneById(planeId);
        logger.debug("Loaded Plane by id " + planeId + " :" + loadedPlane);
        return loadedPlane;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public List<Plane> loadAllPlanes() {
        List<Plane> planes = planeService.loadAllPlanes();
        logger.debug("Found the following Planes in the database: " + planes.size());
        planes.forEach(p -> logger.debug("   " + p));
        return planes;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param plane
     * @return
     * @throws PlaneValidationException
     */
    public Plane updatePlane(Plane plane) throws PlaneValidationException {
        Plane updatedPlane = planeService.updatePlane(plane);
        logger.debug("Plane updated to " + updatedPlane);
        return updatedPlane;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param planeId
     * @throws PlaneValidationException
     */
    public void deletePlaneById(String planeId) throws PlaneValidationException {
        logger.debug("Calling REST-Service to delete plane by id '" + planeId + "'.");
        planeService.deletePlaneById(planeId);
    }
}
