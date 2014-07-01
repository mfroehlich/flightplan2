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

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;
import com.prodyna.pac.flightplan.user.entity.Role;

/**
 * REST interface providing CRUD service methods for {@link AircraftType} objects.
 * 
 * @author mfroehlich
 * 
 */
@Path("aircraftType")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@RolesAllowed({ Role.ADMIN })
public interface AircraftTypeService {

    /**
     * 
     * Persist the specified {@link AircraftType} in the database.
     * 
     * @param aircraftType
     * @return
     * @throws AircraftTypeValidationException
     */
    @POST
    public AircraftType createAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException;

    /**
     * 
     * Load the {@link AircraftType} from the database specified by its id.
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    @PermitAll
    public AircraftType loadAircraftTypeById(@PathParam("id") String id);

    /**
     * 
     * Load all {@link AircraftType}s from the database.
     * 
     * @return
     */
    @GET
    @Path("list")
    @PermitAll
    public List<AircraftType> loadAllAircraftTypes();

    /**
     * 
     * Update the specified {@link AircraftType}.
     * 
     * @param aircraftType
     * @return
     */
    @PUT
    public AircraftType updateAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException;

    /**
     * 
     * Delete the {@link AircraftType} specified by its id.
     * 
     * @param aircraftTypeId
     */
    @DELETE
    @Path("id/{id}")
    public void deleteAircraftTypeById(@PathParam("id") String aircraftTypeId) throws AircraftTypeValidationException;

    /**
     * Check if the {@link AircraftType} specified by its id is referenced by any {@link Plane}.
     * 
     * @param aircraftTypeId
     * @return
     */
    public boolean isAircraftTypeReferencedByPlanes(String aircraftTypeId);

    /**
     * Check if the specified {@link AircraftType} defines a unique description.
     * 
     * @param aircraftType
     * @return
     */
    public boolean isAircraftTypeDescriptionUnique(AircraftType aircraftType);
}
