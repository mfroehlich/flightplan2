/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Path("planereservation")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface PlaneReservationService {

    @POST
    public PlaneReservation createReservation(PlaneReservation reservation);

    @GET
    @Path("list")
    public List<PlaneReservation> loadAllPlaneReservations();

    @GET
    @Path("list/plane/{planeId}/date/{date}")
    public List<PlaneReservation> loadPlaneReservationsByPlaneAndDate(@PathParam("planeId") String planeId,
            @PathParam("date") long dateMillis);

    /**
     * TODO mfroehlich Comment me
     * 
     * @param userId
     * @return
     */
    @GET
    @Path("list/user/{userId}")
    public List<PlaneReservation> loadPlaneReservationsByUserId(@PathParam("userId") String userId);

    @PUT
    public PlaneReservation updateReservation(PlaneReservation reservation);

    @DELETE
    @Path("id/{id}")
    public void deleteReservationById(@PathParam("id") String reservationId);
}
