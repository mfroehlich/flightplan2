/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Ignore
public class PlaneClientServiceTest {

    @Test
    public void testCreatePlane() {

        PlaneClientService planeClientService = new PlaneClientService();
        AircraftTypeClientService aircraftTypeClientService = new AircraftTypeClientService();

        AircraftType type = AircraftTypeFactory.createAircraftType();
        AircraftType createdType;
        try {
            createdType = aircraftTypeClientService.createAircraftType(type);
        } catch (AircraftTypeValidationException e) {
            Assert.fail("Caught unexpected exception.");
            createdType = null;
        }

        Plane plane = PlaneFactory.createPlane(createdType);
        Plane createdPlane;
        try {
            createdPlane = planeClientService.createPlane(plane);
        } catch (PlaneValidationException e) {
            createdPlane = null;
        }

        Assert.assertNotNull(createdPlane.getId());

        plane.setId(createdPlane.getId());
        Assert.assertEquals(plane, createdPlane);
    }
}
