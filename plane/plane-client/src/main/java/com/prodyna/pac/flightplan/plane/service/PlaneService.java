/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

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
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * REST interface providing CRUD service methods for {@link Plane} objects.
 * 
 * @author mfroehlich
 * 
 */
@Path("plane")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@RolesAllowed({ Role.ADMIN })
public interface PlaneService {

    /**
     * 
     * Persist the specified {@link Plane} in the database.
     * 
     * @param plane
     * @return
     * @throws PlaneValidationException
     */
    @POST
    public Plane createPlane(Plane plane) throws PlaneValidationException;

    /**
     * 
     * Load the {@link Plane} from the database specified by its id.
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    @RolesAllowed({ Role.ADMIN, Role.USER })
    public Plane loadPlaneById(@PathParam("id") String planeId);

    /**
     * 
     * Load all {@link Plane}s from the database.
     * 
     * @return
     */
    @GET
    @Path("list")
    @PermitAll
    public List<Plane> loadAllPlanes();

    /**
     * 
     * Update the specified {@link Plane} in the database.
     * 
     * @param plane
     * @return
     * @throws PlaneValidationException
     */
    @PUT
    public Plane updatePlane(Plane plane) throws PlaneValidationException;

    /**
     * 
     * Delete the {@link Plane} specified by its planeId.
     * 
     * @param planeId
     * @throws PlaneValidationException
     */
    @DELETE
    @Path("id/{id}")
    public void deletePlaneById(@PathParam("id") String planeId) throws PlaneValidationException;

    /**
     * Check if the specified plane's name and number plate are unique each.
     * 
     * @param plane
     * @return
     */
    public boolean isPlaneNameAndNumberPlateUnique(Plane plane);
}
