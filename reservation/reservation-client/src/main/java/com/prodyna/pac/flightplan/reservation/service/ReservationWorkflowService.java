/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationItem;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Path("reservation/workflow")
public interface ReservationWorkflowService {

    /**
     * 
     * Cancels the {@link Reservation} specified by id so the item can be reserved again in the period specified by the
     * {@link Reservation}.
     * 
     * @param reservationId The id of the reservation to be aborted.
     * @return
     */
    @PUT
    @Path("cancel")
    public void cancelReservation(String reservationId);

    /**
     * Update the {@link ReservationStatus} of the specified {@link Reservation} to {@link ReservationStatus#LENT} as
     * the {@link ReservationItem} is to be received. This action may only be executed if the period of the reservation
     * contains the current point of time.
     * 
     * @param reservationItemId The id of the {@link Reservation} of which the {@link ReservationItem} is to be
     *            received.
     * @return
     */
    @PUT
    @Path("receiveitem")
    public void receiveReservationItemWithReservationId(String reservationId);

    /**
     * Update the {@link ReservationStatus} of the specified {@link Reservation} to {@link ReservationStatus#RETURNED}
     * as the {@link ReservationItem} is to be returned.
     * 
     * @param reservationId The id of the {@link Reservation} of which the {@link ReservationItem} is to be returned.
     * @return
     */
    @PUT
    @Path("returnitem")
    public void returnReservationItemWithReservationId(String reservationId);

    /**
     * Load the ids of all expired {@link Reservation} objects (meaning the end time is in the past) of which the
     * {@link ReservationItem} is still being lent (meaning the user has not yet returned the {@link ReservationItem}).
     * 
     * @return
     */
    public Collection<String> loadOverdueLentReservationIds();
}
