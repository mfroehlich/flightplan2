/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;
import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;

/**
 * TODO mfroehlich Comment me
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
    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus == ReservationStatus.LENT
                || currentStatus == ReservationStatus.RETURNED) {
            throw new ReservationWorkflowException("Error cancelling reservation due to wrong reservation status.",
                    ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        delegate.cancelReservation(reservationId);
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.RESERVED) {
            throw new ReservationWorkflowException("Error receiving reservation item due to wrong reservation status.",
                    ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        delegate.receiveReservationItemWithReservationId(reservationId);
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) {
        Reservation reservation = reservationService.loadReservationById(reservationId);
        ReservationStatus currentStatus = reservation.getStatus();
        if (currentStatus == null || currentStatus != ReservationStatus.LENT) {
            throw new ReservationWorkflowException("Error returning reservation item due to wrong reservation status.",
                    ReservationErrorCode.WRONG_RESERVATION_STATUS);
        }

        delegate.returnReservationItemWithReservationId(reservationId);
    }
}
