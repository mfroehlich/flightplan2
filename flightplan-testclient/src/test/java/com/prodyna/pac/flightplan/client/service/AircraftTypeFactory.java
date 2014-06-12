/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeFactory {

    public static AircraftType createAircraftType() {
        AircraftType type = new AircraftType();
        type.setId(null);
        type.setDescription("My TestaircraftType");
        return type;
    }
}
