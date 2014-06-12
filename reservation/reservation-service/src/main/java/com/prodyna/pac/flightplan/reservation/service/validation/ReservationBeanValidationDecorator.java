/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service.validation;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Decorator
public class ReservationBeanValidationDecorator implements ReservationService {

    @Inject
    @Delegate
    private ReservationService delegate;

    @Override
    public Reservation createReservation(Reservation reservation) {
        return delegate.createReservation(reservation);
    }

    @Override
    public Reservation loadReservationById(String reservationId) {
        return delegate.loadReservationById(reservationId);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return delegate.updateReservation(reservation);
    }

    @Override
    public void deleteReservationById(String reservationId) {
        delegate.deleteReservationById(reservationId);
    }
}
