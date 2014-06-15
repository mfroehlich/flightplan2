/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.util.Collection;

import javax.annotation.security.RunAs;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@RunAs(Role.GUEST)
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
        overdueReservationIds.forEach(reservationId -> reservationWorkflowService
                .returnReservationItemWithReservationId(reservationId));
    }
}
