/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.util.Collection;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;

/**
 * Singleton method that is scheduled to run once a minute. Searches all overdue reservations in status lent that should
 * have been returned by the pilot and auto-returns them.
 * 
 * @author mfroehlich
 *
 */
@Singleton
@ApplicationScoped
public class PlaneReservationWatcher {

    @Inject
    private ReservationWorkflowService reservationWorkflowService;

    @Inject
    private Logger logger;

    @Schedule(second = "0", minute = "*", hour = "*", persistent = false)
    public void autoReturnOverdueLentPlanes() {
        Collection<String> overdueReservationIds = reservationWorkflowService.loadOverdueLentReservationIds();
        for (String reservationId : overdueReservationIds) {
            try {
                reservationWorkflowService.returnReservationItemWithReservationId(reservationId);
            } catch (ReservationWorkflowException e) {
                logger.error("Error auto-returning reservation '" + reservationId + "'", e);
            }
        }
    }
}
