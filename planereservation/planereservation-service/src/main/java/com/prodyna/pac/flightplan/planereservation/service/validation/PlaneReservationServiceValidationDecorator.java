/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationErrorCode;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationValidationException;
import com.prodyna.pac.flightplan.planereservation.service.PlaneReservationService;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;

/**
 * {@link Decorator} providing validation logic to be executed before {@link PlaneReservationService} methods are
 * called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class PlaneReservationServiceValidationDecorator implements PlaneReservationService {

    @Delegate
    @Inject
    private PlaneReservationService delegate;

    @Inject
    private ErrorCodeCollector<PlaneReservation> collector;

    @Inject
    private Logger logger;

    @Override
    public PlaneReservation createReservation(PlaneReservation reservation) throws PlaneReservationValidationException,
            ReservationValidationException {
        Pilot pilot = reservation.getPilot();
        Plane plane = reservation.getPlane();

        if (pilot == null) {
            collector.addErrorCode(PlaneReservationErrorCode.PILOT_MAY_NOT_BE_NULL);
        }
        if (plane == null) {
            collector.addErrorCode(PlaneReservationErrorCode.PLANE_MAY_NOT_BE_NULL);
        }

        if (pilot != null && plane != null) {
            AircraftType planeAircraftType = plane.getAircraftType();
            List<AircraftType> pilotAircraftTypes = pilot.getAssignedAircraftTypes();

            if (pilotAircraftTypes == null || pilotAircraftTypes.contains(planeAircraftType) == false) {
                throw new PlaneReservationValidationException(
                        "PlaneReservation has validation error: User may not reserve the plane's aircrafttype.",
                        PlaneReservationErrorCode.USER_MAY_NOT_RESERVE_AIRCRAFTTYPE);
            }

            return delegate.createReservation(reservation);
        } else {
            throw new PlaneReservationValidationException("PlaneReservation has validation errors",
                    collector.getErrorCodes());
        }
    }

    @Override
    public PlaneReservation updateReservation(PlaneReservation reservation) throws ReservationValidationException {
        return delegate.updateReservation(reservation);
    }

    @Override
    public void deleteReservationById(String reservationId) throws PlaneReservationValidationException,
            ReservationValidationException {
        if (reservationId == null) {
            throw new PlaneReservationValidationException("Error loading reservation with null id",
                    PlaneReservationErrorCode.ID_MAY_NOT_BE_NULL);
        }

        try {
            delegate.deleteReservationById(reservationId);
        } catch (Exception e) {
            logger.debug("Error deleting reservation " + reservationId + ": " + e);
            throw new PlaneReservationValidationException("Error deleting reservation '" + reservationId + "'.",
                    PlaneReservationErrorCode.RESERVATION_CANNOT_BE_DELETED);
        }
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
