/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.service.PlaneService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PlaneServiceValidationDecorator implements PlaneService {

    @Delegate
    @Inject
    private PlaneService delegate;

    @Override
    public Plane createPlane(Plane plane) {
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
    public Plane updatePlane(Plane plane) {
        return delegate.updatePlane(plane);
    }

    @Override
    public void deletePlaneById(String planeId) {
        delegate.deletePlaneById(planeId);
    }
}
