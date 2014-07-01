/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.TechnicalException;
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
    private Validator validator;

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
    public void deleteReservationById(String reservationId) {
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
     * @throws TechnicalException
     */
    private void executeBeanValidationOnReservation(Reservation reservation) throws ReservationValidationException,
            TechnicalException {
        Collection<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Reservation> violation : constraintViolations) {
                String property = violation.getPropertyPath().toString();
                if (Reservation.PROP_ID.equals(property)) {
                    throw new TechnicalException("Reservation-ID is not set.", ReservationErrorCode.ID_MUST_NOT_BE_NULL);
                } else if (Reservation.PROP_START.equals(property)) {
                    errorCodes.add(ReservationErrorCode.STARTTIME_MUST_NOT_BE_NULL);
                } else if (Reservation.PROP_END.equals(property)) {
                    errorCodes.add(ReservationErrorCode.ENDTIME_MUST_NOT_BE_NULL);
                } else if (Reservation.PROP_RESERVATION_STATUS.equals(property)) {
                    errorCodes.add(ReservationErrorCode.STATUS_MUST_NOT_BE_NULL);
                } else if (Reservation.PROP_USER.equals(property)) {
                    errorCodes.add(ReservationErrorCode.USER_MUST_NOT_BE_NULL);
                } else if (Reservation.PROP_RESERVATION_ITEM.equals(property)) {
                    errorCodes.add(ReservationErrorCode.RESERVATION_ITEM_MUST_NOT_BE_NULL);
                } else {
                    throw new TechnicalException("Unknown error validating reservation.",
                            ReservationErrorCode.UNKNOWN_ERROR);
                }
            }

            throw new ReservationValidationException("Found validation errors.", errorCodes);
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
