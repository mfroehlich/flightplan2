package com.prodyna.pac.flightplan.reservation.service;

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

/**
 * 
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Path("reservation/crud")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface ReservationService {

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param reservation
     * @return
     */
    @POST
    public Reservation createReservation(Reservation reservation);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    public Reservation loadReservationById(@PathParam("id") String reservationId);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param reservation
     * @return
     */
    @PUT
    public Reservation updateReservation(Reservation reservation);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param reservation
     * @return
     */
    @DELETE
    @Path("id/{id}")
    public void deleteReservationById(@PathParam("id") String reservationId);
}
