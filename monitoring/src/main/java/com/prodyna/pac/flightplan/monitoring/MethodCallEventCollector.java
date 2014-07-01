/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;

import com.prodyna.pac.flightplan.monitoring.mbean.MethodCallMonitor;
import com.prodyna.pac.flightplan.monitoring.mbean.MethodCallMonitorMXBean;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Singleton
@Startup
public class MethodCallEventCollector {

    @Inject
    private MBeanServer mbeanServer;

    @Inject
    private Logger logger;

    private final String objectNameStr = MethodCallMonitorMXBean.OBJECTNAME;
    private ObjectName objectName;

    private final Map<String, MethodCallStatistics> methodSignatureToCallStatisticsMap = new HashMap<String, MethodCallStatistics>();

    @PostConstruct
    public void registerInJMX() {
        try {
            objectName = new ObjectName(objectNameStr);
            MethodCallMonitor mbean = new MethodCallMonitor(this);
            mbeanServer.registerMBean(mbean, objectName);
            logger.debug("MBean " + objectNameStr + " registered");
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException
                | MalformedObjectNameException e) {
            logger.error("Error registering MBean " + objectNameStr, e);
            throw new IllegalArgumentException("Error registering MBean " + objectNameStr);
        }
    }

    @PreDestroy
    public void unregisterFromJMX() {
        try {
            mbeanServer.unregisterMBean(objectName);
            logger.debug("MBean " + objectName + " unregistered");
        } catch (MBeanRegistrationException | InstanceNotFoundException e) {
            logger.error("Error unregistering MBean " + objectNameStr, e);
            throw new IllegalArgumentException("Error unregistering MBean " + objectNameStr);
        }
    }

    @Asynchronous
    public void receiveMethodCallEvent(MethodCallEvent event) {
        MethodCallStatistics statistics = methodSignatureToCallStatisticsMap.get(event.getMethodSignature());
        if (statistics == null) {
            statistics = new MethodCallStatistics();
            methodSignatureToCallStatisticsMap.put(event.getMethodSignature(), statistics);
        }
        statistics.processEvent(event);
    }

    /**
     * 
     * TODO mfroehlich Comment me
     */
    public void reset() {
        this.methodSignatureToCallStatisticsMap.clear();
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Map<String, MethodCallStatistics> getMethodCallStatisticsMap() {
        return this.methodSignatureToCallStatisticsMap;
    }
}
