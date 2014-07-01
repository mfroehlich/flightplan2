/**
 * 
 */
package com.prodyna.pac.flightplan.user.service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.flightplan.user.entity.Role;
import com.prodyna.pac.flightplan.user.entity.User;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * REST interface providing CRUD service methods for {@link User} objects.
 * 
 * @author mfroehlich
 *
 */
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Path("user")
@RolesAllowed({ Role.ADMIN })
public interface UserService {

    /**
     * Load the id of the {@link User} specified by its user name.
     * 
     * @param userName
     */
    @GET
    @Path("username/{username}")
    @RolesAllowed({ Role.ADMIN, Role.USER })
    public String loadUserIdByUserName(@PathParam("username") String userName);

    /**
     * 
     * Load the {@link User} specified by its id.
     * 
     * @param userId
     * @return
     */
    @RolesAllowed({ Role.ADMIN, Role.USER })
    public User loadUserById(String userId);

    /**
     * 
     * Encrypt the specified password and return the encrypted string.
     * 
     * @param password
     * @return the encrypted password.
     * @throws UserValidationException
     */
    public String encryptPassword(String password) throws UserValidationException;

    /**
     * 
     * Update the password of the user specified by its id. The password may only be updated with the newPassword if the
     * specified oldPassword matches the current user password.
     * 
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @throws UserValidationException
     */
    @PUT
    @Path("id/{id}/oldpwd/{oldpwd}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updatePassword(@PathParam("id") String userId, @PathParam("oldpwd") String oldPassword,
            String newPassword) throws UserValidationException;
}
