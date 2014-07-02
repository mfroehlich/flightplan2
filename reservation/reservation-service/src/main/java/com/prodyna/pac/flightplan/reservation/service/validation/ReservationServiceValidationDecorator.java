/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import java.util.Collection;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;

/**
 * {@link Decorator} providing validation logic to be executed before {@link ReservationService} methods are called.
 * 
 * @author mfroehlich
 * 
 */
@Decorator
public class ReservationServiceValidationDecorator implements ReservationService {

    @Inject
    private ErrorCodeCollector<Reservation> collector;

    @Inject
    @Delegate
    private ReservationService delegate;

    @Override
    public Reservation createReservation(Reservation reservation) throws ReservationValidationException {

        executeBeanValidationOnReservation(reservation);

        if (reservation.getEnd().before(reservation.getStart())) {
            throw new ReservationValidationException("Starttime must be before endtime.",
                    ReservationErrorCode.STARTTIME_MUST_BE_BEFORE_ENDTIME);
        }

        checkReservationConflict(reservation);

        return delegate.createReservation(reservation);
    }

    @Override
    public Reservation loadReservationById(String reservationId) {
        return delegate.loadReservationById(reservationId);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) throws ReservationValidationException {

        executeBeanValidationOnReservation(reservation);

        if (reservation.getEnd().before(reservation.getStart())) {
            throw new ReservationValidationException("Starttime must be before endtime.",
                    ReservationErrorCode.STARTTIME_MUST_BE_BEFORE_ENDTIME);
        }

        checkReservationConflict(reservation);

        return delegate.updateReservation(reservation);
    }

    @Override
    public void deleteReservationById(String reservationId) throws ReservationValidationException {
        Reservation reservation = loadReservationById(reservationId);
        if (reservation == null) {
            throw new ReservationValidationException("Reservation to be deleted does not exist anymore.",
                    ReservationErrorCode.RESERVATION_TO_BE_DELETED_DOES_NOT_EXIST);
        }
        delegate.deleteReservationById(reservationId);
    }

    @Override
    public Collection<String> findConflictingReservationIds(Reservation reservation) {
        return delegate.findConflictingReservationIds(reservation);
    }

    /**
     * Validate the {@link Reservation} via bean validation and translate the constraint violations into according error
     * codes.
     * 
     * @param reservation
     * @throws ReservationValidationException
     */
    private void executeBeanValidationOnReservation(Reservation reservation) throws ReservationValidationException {

        collector.validateProperty(reservation, Reservation.PROP_ID, ReservationErrorCode.ID_MUST_NOT_BE_NULL);
        collector
                .validateProperty(reservation, Reservation.PROP_START, ReservationErrorCode.STARTTIME_MUST_NOT_BE_NULL);
        collector.validateProperty(reservation, Reservation.PROP_END, ReservationErrorCode.ENDTIME_MUST_NOT_BE_NULL);
        collector.validateProperty(reservation, Reservation.PROP_RESERVATION_STATUS,
                ReservationErrorCode.STATUS_MUST_NOT_BE_NULL);
        collector.validateProperty(reservation, Reservation.PROP_USER, ReservationErrorCode.USER_MUST_NOT_BE_NULL);
        collector.validateProperty(reservation, Reservation.PROP_RESERVATION_ITEM,
                ReservationErrorCode.RESERVATION_ITEM_MUST_NOT_BE_NULL);

        if (collector.hasErrorCodes()) {
            throw new ReservationValidationException("Found validation errors.", collector.getErrorCodes());
        }
    }

    /**
     * Checks if the specified {@link Reservation} conflicts any other {@link Reservation} stored in the database.
     * 
     * @param reservation
     * @throws ReservationValidationException
     */
    private void checkReservationConflict(Reservation reservation) throws ReservationValidationException {
        Collection<String> conflictingReservationIds = findConflictingReservationIds(reservation);
        if (conflictingReservationIds.size() > 0) {
            throw new ReservationValidationException("Reservation " + reservation.getId()
                    + " cannot be stored due to conflicts to other reservations.",
                    ReservationErrorCode.RESERVATION_CONFLICT);
        }
    }
}
