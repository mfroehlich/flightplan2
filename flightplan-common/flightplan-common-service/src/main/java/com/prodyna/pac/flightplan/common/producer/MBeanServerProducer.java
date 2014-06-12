/**
 * 
 */
package com.prodyna.pac.flightplan.common.producer;

import java.lang.management.ManagementFactory;

import javax.enterprise.inject.Produces;
import javax.management.MBeanServer;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
public class MBeanServerProducer {

    @Produces
    public MBeanServer createMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }
}
