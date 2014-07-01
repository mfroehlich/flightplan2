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

import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;

/**
 * Stateless EJB - Implementation of {@link ReservationWorkflowService} providing service methods concerning the status
 * of a {@link Reservation}.
 * 
 * @author mfroehlich
 *
 */
@Stateless
@Logging
@MethodCallsMonitored
public class ReservationWorkflowBean implements ReservationWorkflowService {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @Override
    public void cancelReservation(String reservationId) throws ReservationWorkflowException {
        logger.debug("Cancelling reservation " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.CANCELLED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void receiveReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        logger.debug("Receiving reservation item for reservation id " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.LENT);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public void returnReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException {
        logger.debug("Returing reservation item for reservation id " + reservationId);
        Query query = em.createNamedQuery(Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID);
        query.setParameter("status", ReservationStatus.RETURNED);
        query.setParameter("id", reservationId);
        query.executeUpdate();
    }

    @Override
    public Collection<String> loadOverdueLentReservationIds() {
        Query query = em.createNamedQuery(Reservation.QUERY_LOAD_OVERDUE_RESERVATIONIDS);
        query.setParameter("now", new Date());
        query.setParameter("statusLent", ReservationStatus.LENT);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }
}
