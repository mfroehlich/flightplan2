/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizationInterceptor;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneErrorCode;
import com.prodyna.pac.flightplan.plane.exception.PlaneNotFoundException;
import com.prodyna.pac.flightplan.user.entity.Role;

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
public class PlaneBean implements PlaneService {

    @Inject
    private EntityManager em;

    @Override
    public Plane createPlane(Plane plane) {
        em.persist(plane);
        return plane;
    }

    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public Plane loadPlaneById(String id) {
        Plane plane = em.find(Plane.class, id);
        return plane;
    }

    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public List<Plane> loadAllPlanes() {
        Query query = em.createNamedQuery(Plane.QUERY_LOAD_ALL_PLANES);
        @SuppressWarnings("unchecked")
        List<Plane> planes = query.getResultList();
        return planes;
    }

    @Override
    public Plane updatePlane(Plane plane) {
        Plane updatedPlane = em.merge(plane);
        return updatedPlane;
    }

    @Override
    public void deletePlaneById(String planeId) {
        Plane plane = loadPlaneById(planeId);
        if (plane == null) {
            throw new PlaneNotFoundException("Plane with id '" + planeId
                    + "' not found in the database. Aborting delete action.", PlaneErrorCode.PLANE_NOT_FOUND_BY_ID);
        }

        em.remove(plane);
    }
}
