/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;

/**
 * 
 * Stateless EJB - Implementation of {@link AircraftTypeService} providing CRUD service methods for {@link AircraftType}
 * .
 * 
 * @author mfroehlich
 * 
 */
@Stateless
@Logging
@MethodCallsMonitored
public class AircraftTypeBean implements AircraftTypeService {

    @Inject
    private EntityManager em;

    @Resource
    private SessionContext context;

    @Override
    public AircraftType createAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException {
        em.persist(aircraftType);
        return aircraftType;
    }

    @Override
    public AircraftType loadAircraftTypeById(String id) {
        AircraftType aircraftType = em.find(AircraftType.class, id);
        return aircraftType;
    }

    @Override
    public List<AircraftType> loadAllAircraftTypes() {
        Query query = em.createNamedQuery(AircraftType.QUERY_LOAD_ALL_TYPES);
        @SuppressWarnings("unchecked")
        List<AircraftType> aircraftTypes = query.getResultList();
        return aircraftTypes;
    }

    @Override
    public AircraftType updateAircraftType(AircraftType aircraftType) throws AircraftTypeValidationException {
        AircraftType updatedAircraftType = em.merge(aircraftType);
        return updatedAircraftType;
    }

    @Override
    public void deleteAircraftTypeById(String aircraftTypeId) throws AircraftTypeValidationException {
        AircraftType aircraftType = loadAircraftTypeById(aircraftTypeId);
        em.remove(aircraftType);
    }

    @Override
    public boolean isAircraftTypeReferencedByPlanes(String aircraftTypeId) {
        Query query = em.createNamedQuery(Plane.QUERY_COUNT_PLANES_REFERENCING_AIRCRAFTTYPE);
        query.setParameter("aircraftTypeId", aircraftTypeId);
        Long numberOfPlanesReferencingAircraftType = (Long) query.getSingleResult();
        return numberOfPlanesReferencingAircraftType > 0;
    }

    @Override
    public boolean isAircraftTypeDescriptionUnique(AircraftType aircraftType) {
        Query query = em.createNamedQuery(AircraftType.QUERY_COUNT_OTHER_AIRCRAFTTYPES_DEFINING_DESCRIPTION);
        query.setParameter("aircraftTypeId", aircraftType.getId());
        query.setParameter("description", aircraftType.getDescription());
        Long numberOfAircraftTypesDefiningDescription = (Long) query.getSingleResult();
        return numberOfAircraftTypesDefiningDescription == 0;
    }
}
