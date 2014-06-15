/**
 * 
 */
package com.prodyna.pac.flightplan.user.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("user")
public interface UserService {

    /**
     * TODO mfroehlich Comment me
     * 
     * @param userName
     */
    @GET
    @Path("username/{username}")
    public String loadUserIdByUserName(@PathParam("username") String userName);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param password
     * @return
     */
    public String encryptPassword(String password);

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    @PUT
    @Path("id/{id}/oldpwd/{oldpwd}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updatePassword(@PathParam("id") String userId, @PathParam("oldpwd") String oldPassword,
            String newPassword);
}
