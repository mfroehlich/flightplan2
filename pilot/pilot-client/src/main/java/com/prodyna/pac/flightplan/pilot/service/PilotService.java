/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

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

import com.prodyna.pac.flightplan.pilot.entity.Pilot;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Path("pilot")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface PilotService {

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     */
    @POST
    public Pilot createPilot(Pilot pilot);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    public Pilot loadPilotById(@PathParam("id") String id);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    @GET
    @Path("list")
    public List<Pilot> loadAllPilots();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilot
     * @return
     */
    @PUT
    public Pilot updatePilot(Pilot pilot);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     */
    @DELETE
    @Path("id/{id}")
    public void deletePilotById(@PathParam("id") String pilotId);
}
