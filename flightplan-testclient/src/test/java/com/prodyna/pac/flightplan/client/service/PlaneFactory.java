/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneFactory {

    public static Plane createPlane(AircraftType type) {
        Plane plane = new Plane();
        plane.setId(null);
        plane.setVersion(1);
        plane.setName("My Testplane");
        plane.setNumberPlate("MY-TEST-123");
        plane.setAircraftType(type);

        return plane;
    }
}
