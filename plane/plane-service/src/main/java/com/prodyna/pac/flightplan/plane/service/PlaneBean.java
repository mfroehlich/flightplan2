/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;

/**
 * Stateless EJB - Implementation of {@link PlaneService} providing CRUD service methods for {@link Plane}.
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
public class PlaneBean implements PlaneService {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @Override
    public Plane createPlane(Plane plane) throws PlaneValidationException {
        em.persist(plane);
        return plane;
    }

    @Override
    public Plane loadPlaneById(String id) {
        Plane plane = em.find(Plane.class, id);
        return plane;
    }

    @Override
    public List<Plane> loadAllPlanes() {
        Query query = em.createNamedQuery(Plane.QUERY_LOAD_ALL_PLANES);
        @SuppressWarnings("unchecked")
        List<Plane> planes = query.getResultList();
        return planes;
    }

    @Override
    public Plane updatePlane(Plane plane) throws PlaneValidationException {
        Plane updatedPlane = em.merge(plane);
        return updatedPlane;
    }

    @Override
    public void deletePlaneById(String planeId) throws PlaneValidationException {
        Plane plane = loadPlaneById(planeId);
        em.remove(plane);
        em.flush();
    }

    @Override
    public boolean isPlaneNameAndNumberPlateUnique(Plane plane) {

        Query query = em.createNamedQuery(Plane.QUERY_CHECK_NAME_AND_NUMBERPLATE_UNIQUE);
        query.setParameter("id", plane.getId());
        query.setParameter("name", plane.getName());
        query.setParameter("numberPlate", plane.getNumberPlate());
        Long count = (Long) query.getSingleResult();

        return count == 0;
    }
}
