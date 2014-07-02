package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * 
 * REST interface providing CRUD service methods for {@link Reservation} objects.
 * 
 * @author mfroehlich
 * 
 */
@Path("reservation/crud")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@RolesAllowed({ Role.ADMIN, Role.USER })
public interface ReservationService {

    /**
     * 
     * Persist the specified {@link Reservation} in the database.
     * 
     * @param reservation
     * @return
     * @throws ReservationValidationException
     */
    @POST
    public Reservation createReservation(Reservation reservation) throws ReservationValidationException;

    /**
     * 
     * Load the {@link Reservation} specified by its id from the database.
     * 
     * @param reservationId
     * @return
     */
    @GET
    @Path("id/{id}")
    @PermitAll
    public Reservation loadReservationById(@PathParam("id") String reservationId);

    /**
     * 
     * Update the {@link Reservation} in the database.
     * 
     * @param reservation
     * @return
     * @throws ReservationValidationException
     */
    @PUT
    public Reservation updateReservation(Reservation reservation) throws ReservationValidationException;

    /**
     * 
     * Delete the {@link Reservation} specified by its id from the database.
     * 
     * @param reservationId
     * @return
     * @throws ReservationValidationException
     */
    @DELETE
    @Path("id/{id}")
    public void deleteReservationById(@PathParam("id") String reservationId) throws ReservationValidationException;

    /**
     * Determines the ids of all already stored {@link Reservation}s in the database that could conflict the creation of
     * the specified {@link Reservation}.
     * 
     * @param reservation
     * @return
     */
    public Collection<String> findConflictingReservationIds(Reservation reservation);
}
