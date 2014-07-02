/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.util.List;

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

import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationValidationException;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;
import com.prodyna.pac.flightplan.user.entity.Role;
import com.prodyna.pac.flightplan.user.entity.User;

/**
 * REST interface providing CRUD service methods for {@link PlaneReservation} objects.
 * 
 * @author mfroehlich
 *
 */
@Path("planereservation")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@RolesAllowed({ Role.ADMIN, Role.USER })
public interface PlaneReservationService {

    /**
     * 
     * Persist the specified {@link PlaneReservation} in the database.
     * 
     * @param reservation
     * @return
     * @throws PlaneReservationValidationException
     * @throws ReservationValidationException
     */
    @POST
    public PlaneReservation createReservation(PlaneReservation reservation) throws PlaneReservationValidationException,
            ReservationValidationException;

    /**
     * 
     * Load all {@link PlaneReservation} objects from the database.
     * 
     * @return
     */
    @GET
    @Path("list")
    @PermitAll
    public List<PlaneReservation> loadAllPlaneReservations();

    /**
     * 
     * Load all {@link PlaneReservation} objects from the database that have assigned the {@link Plane} specified by id
     * and that take place at the specified time (dateMillis).
     * 
     * @param planeId
     * @param dateMillis
     * @return
     */
    @GET
    @Path("list/plane/{planeId}/date/{date}")
    @PermitAll
    public List<PlaneReservation> loadPlaneReservationsByPlaneAndDate(@PathParam("planeId") String planeId,
            @PathParam("date") long dateMillis);

    /**
     * Load all {@link PlaneReservation} objects from the database that are assigned to the {@link User} specified by
     * its userId.
     * 
     * @param userId
     * @return
     */
    @GET
    @Path("list/user/{userId}")
    public List<PlaneReservation> loadPlaneReservationsByUserId(@PathParam("userId") String userId);

    /**
     * 
     * Update the specified {@link PlaneReservation} in the database.
     * 
     * @param reservation
     * @return
     * @throws ReservationValidationException
     */
    @PUT
    public PlaneReservation updateReservation(PlaneReservation reservation) throws ReservationValidationException;

    /**
     * 
     * Delete the {@link PlaneReservation} from the database specified by its id.
     * 
     * @param reservationId
     * @throws PlaneReservationValidationException
     * @throws ReservationValidationException
     */
    @DELETE
    @Path("id/{id}")
    public void deleteReservationById(@PathParam("id") String reservationId)
            throws PlaneReservationValidationException, ReservationValidationException;
}
