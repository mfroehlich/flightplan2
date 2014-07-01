/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationErrorCode;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationValidationException;
import com.prodyna.pac.flightplan.planereservation.service.PlaneReservationService;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;

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
    public PlaneReservation createReservation(PlaneReservation reservation) throws PlaneReservationValidationException,
            ReservationValidationException {
        Pilot pilot = reservation.getPilot();
        Plane plane = reservation.getPlane();

        AircraftType planeAircraftType = plane.getAircraftType();
        List<AircraftType> pilotAircraftTypes = pilot.getAssignedAircraftTypes();

        if (pilotAircraftTypes == null || pilotAircraftTypes.contains(planeAircraftType) == false) {
            throw new PlaneReservationValidationException(
                    "PlaneReservation has validation error: User may not reserve the plane's aircrafttype.",
                    PlaneReservationErrorCode.USER_MAY_NOT_RESERVE_AIRCRAFTTYPE);
        }

        return delegate.createReservation(reservation);
    }

    @Override
    public PlaneReservation updateReservation(PlaneReservation reservation) throws ReservationValidationException {
        return delegate.updateReservation(reservation);
    }

    @Override
    public void deleteReservationById(String reservationId) {
        delegate.deleteReservationById(reservationId);
    }

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
