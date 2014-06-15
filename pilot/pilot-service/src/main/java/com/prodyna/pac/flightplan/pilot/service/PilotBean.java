/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizationInterceptor;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotErrorCode;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.user.entity.Role;
import com.prodyna.pac.flightplan.user.entity.UserToRoleMapping;
import com.prodyna.pac.flightplan.user.service.UserService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
@Interceptors(AuthorizationInterceptor.class)
@AuthorizedRoles({ Role.ADMIN })
public class PilotBean implements PilotService {

    @Inject
    private EntityManager em;

    @Inject
    private UserService userService;

    @Inject
    private Logger logger;

    @Override
    public Pilot createPilot(Pilot pilot) {
        logger.debug("Encrypting pilot password before persisting.");
        String encryptedPassword = userService.encryptPassword(pilot.getPassword());
        pilot.setPassword(encryptedPassword);

        logger.debug("Persisting pilot: " + pilot);
        em.persist(pilot);

        Role role = new Role();
        role.setId("2");
        UserToRoleMapping mapping = new UserToRoleMapping();
        mapping.setId(UUID.randomUUID().toString());
        mapping.setRole(role);
        mapping.setUser(pilot);
        em.persist(mapping);

        return pilot;
    }

    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public Pilot loadPilotById(String pilotId) {
        Pilot pilot = em.find(Pilot.class, pilotId);
        return pilot;
    }

    @SuppressWarnings("unchecked")
    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public List<Pilot> loadAllPilots() {
        Query query = em.createNamedQuery(Pilot.QUERY_LOAD_ALL_PILOTS);
        List<Pilot> pilotList = query.getResultList();
        return pilotList;
    }

    @Override
    public Pilot updatePilot(Pilot pilot) {
        Pilot mergedPilot = em.merge(pilot);
        return mergedPilot;
    }

    @Override
    public void deletePilotById(String pilotId) {
        Pilot pilotToBeDeleted = loadPilotById(pilotId);
        if (pilotToBeDeleted == null) {
            throw new PilotNotFoundException("Error loading pilot to be deleted", PilotErrorCode.PILOT_NOT_FOUND_BY_ID);
        }

        em.remove(pilotToBeDeleted);
    }
}
