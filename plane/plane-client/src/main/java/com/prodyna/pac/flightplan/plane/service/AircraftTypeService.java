/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

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

import com.prodyna.pac.flightplan.plane.entity.AircraftType;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Path("aircraftType")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface AircraftTypeService {

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param aircraftType
     * @return
     */
    @POST
    public AircraftType createAircraftType(AircraftType aircraftType);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    public AircraftType loadAircraftTypeById(@PathParam("id") String id);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    @GET
    @Path("list")
    public List<AircraftType> loadAllAircraftTypes();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param aircraftType
     * @return
     */
    @PUT
    public AircraftType updateAircraftType(AircraftType aircraftType);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param aircraftTypeId
     */
    @DELETE
    @Path("id/{id}")
    public void deleteAircraftTypeById(@PathParam("id") String aircraftTypeId);
}
