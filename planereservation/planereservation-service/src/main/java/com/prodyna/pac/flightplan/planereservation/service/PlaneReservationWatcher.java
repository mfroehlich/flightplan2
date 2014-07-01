/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.util.Collection;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Singleton
@ApplicationScoped
public class PlaneReservationWatcher {

    @Inject
    private EntityManager em;

    @Inject
    private ReservationWorkflowService reservationWorkflowService;

    @Inject
    private Logger logger;

    @Schedule(second = "0", minute = "*", hour = "*")
    public void autoReturnOverdueLentPlanes() {
        Collection<String> overdueReservationIds = reservationWorkflowService.loadOverdueLentReservationIds();
        for (String reservationId : overdueReservationIds) {
            try {
                reservationWorkflowService.returnReservationItemWithReservationId(reservationId);
            } catch (ReservationWorkflowException e) {
                logger.error("Error auto-returing reservation '" + reservationId + "'", e);
            }
        }
    }
}
