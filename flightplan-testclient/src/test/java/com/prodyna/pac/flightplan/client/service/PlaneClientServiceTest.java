/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;

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
        AircraftType createdType = aircraftTypeClientService.createAircraftType(type);

        Plane plane = PlaneFactory.createPlane(createdType);
        Plane createdPlane = planeClientService.createPlane(plane);

        Assert.assertNotNull(createdPlane.getId());

        plane.setId(createdPlane.getId());
        Assert.assertEquals(plane, createdPlane);
    }
}
