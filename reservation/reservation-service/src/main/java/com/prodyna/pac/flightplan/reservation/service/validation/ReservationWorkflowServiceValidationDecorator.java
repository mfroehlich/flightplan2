/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;
import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * {@link Decorator} providing validation logic to be executed before {@link ReservationWorkflowService} methods are
 * called.
 * 
 * @author mfroehlich
 *
 */
@Decorator
public class ReservationWorkflowServiceValidationDecorator implements ReservationWorkflowService {

    @Delegate
    @Inject
    private ReservationWorkflowService delegate;

    @Inject
    private ReservationService reservationService;

    @Override
    public void cancelReservation(String reservationId) throws ReservationWorkflowException {
        Collection<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus == ReservationStatus.CANCELLED
                || currentStatus == ReservationStatus.RETURNED) {
            errorCodes.add(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        if (errorCodes.size() > 0) {
            throw new ReservationWorkflowException("Error cancelling reservation for reservation " + reservationId,
                    errorCodes);
        }

        delegate.cancelReservation(reservationId);
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        Collection<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.RESERVED) {
            errorCodes.add(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        Date start = reservation.getStart();
        Date end = reservation.getEnd();
        LocalDateTime now = LocalDateTime.now();
        if (start == null || LocalDateConverter.dateToLocalDateTime(start).isAfter(now) || end == null
                || LocalDateConverter.dateToLocalDateTime(end).isBefore(now)) {
            errorCodes.add(ReservationErrorCode.RESERVATION_DATE_IN_PAST_OR_FUTURE);
        }

        if (errorCodes.size() > 0) {
            throw new ReservationWorkflowException("Error receiving reservation item for reservation " + reservationId,
                    errorCodes);
        }

        delegate.receiveReservationItemWithReservationId(reservationId);
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        Collection<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.LENT) {
            errorCodes.add(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        if (errorCodes.size() > 0) {
            throw new ReservationWorkflowException("Error returning reservation item for reservation " + reservationId,
                    errorCodes);
        }

        delegate.returnReservationItemWithReservationId(reservationId);
    }

    @Override
    public Collection<String> loadOverdueLentReservationIds() {
        return delegate.loadOverdueLentReservationIds();
    }
}
