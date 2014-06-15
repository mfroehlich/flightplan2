/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizationInterceptor;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
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
public class AircraftTypeBean implements AircraftTypeService {

    @Inject
    private EntityManager em;

    @Resource
    private SessionContext context;

    @Override
    public AircraftType createAircraftType(AircraftType aircraftType) {
        em.persist(aircraftType);
        return aircraftType;
    }

    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public AircraftType loadAircraftTypeById(String id) {
        AircraftType aircraftType = em.find(AircraftType.class, id);
        return aircraftType;
    }

    @Override
    @AuthorizedRoles({ Role.USER, Role.GUEST })
    public List<AircraftType> loadAllAircraftTypes() {
        Query query = em.createNamedQuery(AircraftType.QUERY_LOAD_ALL_TYPES);
        @SuppressWarnings("unchecked")
        List<AircraftType> aircraftTypes = query.getResultList();
        return aircraftTypes;
    }

    @Override
    public AircraftType updateAircraftType(AircraftType aircraftType) {
        AircraftType updatedAircraftType = em.merge(aircraftType);
        return updatedAircraftType;
    }

    @Override
    public void deleteAircraftTypeById(String aircraftTypeId) {
        AircraftType aircraftType = loadAircraftTypeById(aircraftTypeId);
        em.remove(aircraftType);
    }
}
