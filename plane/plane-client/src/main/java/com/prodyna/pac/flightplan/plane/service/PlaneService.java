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

import com.prodyna.pac.flightplan.plane.entity.Plane;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Path("plane")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface PlaneService {

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param plane
     * @return
     */
    @POST
    public Plane createPlane(Plane plane);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param id
     * @return
     */
    @GET
    @Path("id/{id}")
    public Plane loadPlaneById(@PathParam("id") String planeId);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    @GET
    @Path("list")
    public List<Plane> loadAllPlanes();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param plane
     * @return
     */
    @PUT
    public Plane updatePlane(Plane plane);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param planeId
     */
    @DELETE
    @Path("id/{id}")
    public void deletePlaneById(@PathParam("id") String planeId);
}
