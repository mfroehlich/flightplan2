/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
// @Interceptors(AuthorizationInterceptor.class)
@AuthorizedRoles({ Role.ADMIN, Role.USER })
public class ReservationBean implements ReservationService {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @Override
    public Reservation createReservation(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

    @Override
    @AuthorizedRoles({ Role.GUEST })
    public Reservation loadReservationById(String id) {
        Reservation reservation = em.find(Reservation.class, id);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation updatedReservation = em.merge(reservation);
        return updatedReservation;
    }

    @Override
    public void deleteReservationById(String reservationId) {
        Reservation reservation = loadReservationById(reservationId);
        em.remove(reservation);
    }
}
