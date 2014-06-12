/**
 * 
 */
package com.prodyna.pac.flightplan.common.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
public class EntityManagerProducer {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public EntityManager produceEntityManager() {
        return entityManager;
    }
}
