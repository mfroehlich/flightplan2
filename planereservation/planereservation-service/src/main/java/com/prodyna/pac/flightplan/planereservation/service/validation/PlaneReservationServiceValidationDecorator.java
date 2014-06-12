/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.service.PlaneReservationService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PlaneReservationServiceValidationDecorator implements PlaneReservationService {

    @Delegate
    @Inject
    private PlaneReservationService delegate;

    @Override
    public List<PlaneReservation> loadAllPlaneReservations() {
        return delegate.loadAllPlaneReservations();
    }

    @Override
    public List<PlaneReservation> loadPlaneReservationsByPlaneAndDate(String planeId, long dateMillis) {
        return delegate.loadPlaneReservationsByPlaneAndDate(planeId, dateMillis);
    }

    @Override
    public List<PlaneReservation> loadPlaneReservationsByUserId(String userId) {
        return delegate.loadPlaneReservationsByUserId(userId);
    }
}
