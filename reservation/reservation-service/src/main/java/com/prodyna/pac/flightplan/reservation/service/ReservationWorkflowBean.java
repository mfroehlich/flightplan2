/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
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
public class ReservationWorkflowBean implements ReservationWorkflowService {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @Override
    public void cancelReservation(String reservationId) {
        logger.debug("Cancelling reservation " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.CANCELLED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) {
        logger.debug("Receiving reservation item for reservation id " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.LENT);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) {
        logger.debug("Returing reservation item for reservation id " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.RETURNED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    @AuthorizedRoles({ Role.GUEST })
    public Collection<String> loadOverdueLentReservationIds() {
        Query query = em.createNamedQuery(Reservation.QUERY_LOAD_OVERDUE_RESERVATIONIDS);
        query.setParameter("now", new Date());
        query.setParameter("statusLent", ReservationStatus.LENT);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }
}
