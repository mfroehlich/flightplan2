/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.util.List;

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

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.user.entity.Role;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * REST interface providing CRUD service methods for {@link Pilot} objects.
 * 
 * @author mfroehlich
 * 
 */
@Path("pilot")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@RolesAllowed({ Role.ADMIN })
public interface PilotService {

    /**
     * 
     * Persist the specified {@link Pilot} in the database.
     * 
     * @param pilot
     * @return
     * @throws UserValidationException
     */
    @POST
    public Pilot createPilot(Pilot pilot) throws PilotValidationException, UserValidationException;

    /**
     * 
     * Load the {@link Pilot} specified by its id from the database.
     * 
     * @param id
     * @return
     * @throws PilotNotFoundException
     */
    @GET
    @Path("id/{id}")
    @RolesAllowed({ Role.ADMIN, Role.USER })
    public Pilot loadPilotById(@PathParam("id") String id) throws PilotNotFoundException;

    /**
     * 
     * Load all {@link Pilot}s from the database.
     * 
     * @return
     */
    @GET
    @Path("list")
    public List<Pilot> loadAllPilots();

    /**
     * 
     * Update the specified {@link Pilot}.
     * 
     * @param pilot
     * @return
     * @throws UserValidationException
     */
    @PUT
    public Pilot updatePilot(Pilot pilot) throws PilotValidationException, UserValidationException;

    /**
     * 
     * Delete the {@link Pilot} specified by its pilotId.
     * 
     * @param pilotId
     */
    @DELETE
    @Path("id/{id}")
    public void deletePilotById(@PathParam("id") String pilotId) throws PilotNotFoundException,
            PilotValidationException;
}
