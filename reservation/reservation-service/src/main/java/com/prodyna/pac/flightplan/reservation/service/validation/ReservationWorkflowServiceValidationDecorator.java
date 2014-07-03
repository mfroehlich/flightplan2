/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.exception.ErrorCodeCollector;
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
    private Logger logger;

    @Inject
    private ReservationService reservationService;

    @Inject
    private ErrorCodeCollector<Reservation> collector;

    @Override
    public void cancelReservation(String reservationId) throws ReservationWorkflowException {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus == ReservationStatus.CANCELLED
                || currentStatus == ReservationStatus.RETURNED) {
            logger.warn("Cannot cancel reservation due to wrong status, returning error code "
                    + ReservationErrorCode.WRONG_RESERVATION_STATUS);
            collector.addErrorCode(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        if (collector.hasErrorCodes()) {
            throw new ReservationWorkflowException("Error cancelling reservation for reservation " + reservationId,
                    collector.getErrorCodes());
        }

        delegate.cancelReservation(reservationId);
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.RESERVED) {
            logger.warn("Cannot receive reservation item due to wrong status, returning error code "
                    + ReservationErrorCode.WRONG_RESERVATION_STATUS);
            collector.addErrorCode(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        Date start = reservation.getStart();
        Date end = reservation.getEnd();
        LocalDateTime now = LocalDateTime.now();
        if (start == null || LocalDateConverter.dateToLocalDateTime(start).isAfter(now) || end == null
                || LocalDateConverter.dateToLocalDateTime(end).isBefore(now)) {
            logger.warn("Cannot receive reservation item because reservation period is in past or future, returning error code "
                    + ReservationErrorCode.RESERVATION_DATE_IN_PAST_OR_FUTURE);
            collector.addErrorCode(ReservationErrorCode.RESERVATION_DATE_IN_PAST_OR_FUTURE);
        }

        if (collector.hasErrorCodes()) {
            throw new ReservationWorkflowException("Error receiving reservation item for reservation " + reservationId,
                    collector.getErrorCodes());
        }

        delegate.receiveReservationItemWithReservationId(reservationId);
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.LENT) {
            logger.warn("Cannot return reservation item due to wrong status, returning error code "
                    + ReservationErrorCode.WRONG_RESERVATION_STATUS);
            collector.addErrorCode(ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        if (collector.hasErrorCodes()) {
            throw new ReservationWorkflowException("Error returning reservation item for reservation " + reservationId,
                    collector.getErrorCodes());
        }

        delegate.returnReservationItemWithReservationId(reservationId);
    }

    @Override
    public Collection<String> loadOverdueLentReservationIds() {
        return delegate.loadOverdueLentReservationIds();
    }
}
