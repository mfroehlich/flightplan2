/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationItem;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * REST interface providing lifecycle workflow service methods for {@link Reservation} objects concerning the
 * {@link ReservationStatus} and according chances during the lifecycle of a {@link Reservation}.
 * 
 * @author mfroehlich
 * 
 */
@Path("reservation/workflow")
@RolesAllowed({ Role.ADMIN, Role.USER })
public interface ReservationWorkflowService {

    /**
     * 
     * Cancels the {@link Reservation} specified by id so the item can be reserved again in the period specified by the
     * {@link Reservation}.
     * 
     * @param reservationId The id of the reservation to be aborted.
     * @return
     * @throws ReservationWorkflowException
     */
    @PUT
    @Path("cancel")
    public void cancelReservation(String reservationId) throws ReservationWorkflowException;

    /**
     * Update the {@link ReservationStatus} of the specified {@link Reservation} to {@link ReservationStatus#LENT} as
     * the {@link ReservationItem} is to be received. This action may only be executed if the period of the reservation
     * contains the current point of time.
     * 
     * @param reservationItemId The id of the {@link Reservation} of which the {@link ReservationItem} is to be
     *            received.
     * @return
     * @throws ReservationWorkflowException
     */
    @PUT
    @Path("receiveitem")
    public void receiveReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException;

    /**
     * Update the {@link ReservationStatus} of the specified {@link Reservation} to {@link ReservationStatus#RETURNED}
     * as the {@link ReservationItem} is to be returned.
     * 
     * @param reservationId The id of the {@link Reservation} of which the {@link ReservationItem} is to be returned.
     * @return
     * @throws ReservationWorkflowException
     */
    @PUT
    @Path("returnitem")
    public void returnReservationItemWithReservationId(String reservationId) throws ReservationWorkflowException;

    /**
     * Load the ids of all expired {@link Reservation} objects (meaning the end time is in the past) of which the
     * {@link ReservationItem} is still being lent (meaning the user has not yet returned the {@link ReservationItem}).
     * 
     * @return
     */
    public Collection<String> loadOverdueLentReservationIds();
}
