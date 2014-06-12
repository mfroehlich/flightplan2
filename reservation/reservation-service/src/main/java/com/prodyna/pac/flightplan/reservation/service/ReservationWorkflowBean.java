/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.entity.Role;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizationInterceptor;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Stateless
@Logging
@MethodCallsMonitored
@Interceptors(AuthorizationInterceptor.class)
@AuthorizedRoles({ Role.ADMIN, Role.USER })
public class ReservationWorkflowBean implements ReservationWorkflowService {

    @Inject
    private EntityManager em;

    @Override
    public void cancelReservation(String reservationId) {
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.CANCELLED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) {
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.LENT);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) {
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.RETURNED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }
}
