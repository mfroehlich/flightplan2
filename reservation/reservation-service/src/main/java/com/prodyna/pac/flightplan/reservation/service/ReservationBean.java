/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.exception.TechnicalException;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;

/**
 * 
 * Stateless EJB - Implementation of {@link ReservationService} providing CRUD service methods for {@link Reservation}.
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
public class ReservationBean implements ReservationService {

    @Inject
    private EntityManager em;

    @Override
    public Reservation createReservation(Reservation reservation) throws ReservationValidationException {
        em.persist(reservation);
        return reservation;
    }

    @Override
    public Reservation loadReservationById(String id) {
        Reservation reservation = em.find(Reservation.class, id);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) throws ReservationValidationException {
        Reservation updatedReservation = em.merge(reservation);
        return updatedReservation;
    }

    @Override
    public void deleteReservationById(String reservationId) {
        Reservation reservation = loadReservationById(reservationId);
        if (reservation == null) {
            throw new TechnicalException("Reservation to be deleted does not exist anymore.",
                    ReservationErrorCode.RESERVATION_TO_BE_DELETED_DOES_NOT_EXIST);
        }
        em.remove(reservation);
    }

    @Override
    public Collection<String> findConflictingReservationIds(Reservation reservation) {

        Query query = em.createNamedQuery(Reservation.QUERY_FIND_RESERVATION_CONFLICTS);
        query.setParameter("start", reservation.getStart());
        query.setParameter("end", reservation.getEnd());
        query.setParameter("statusLent", ReservationStatus.LENT);
        query.setParameter("statusReserved", ReservationStatus.RESERVED);
        query.setParameter("item", reservation.getItem());
        query.setParameter("id", reservation.getId());

        @SuppressWarnings("unchecked")
        Collection<String> conflictingIds = query.getResultList();

        return conflictingIds;
    }
}
