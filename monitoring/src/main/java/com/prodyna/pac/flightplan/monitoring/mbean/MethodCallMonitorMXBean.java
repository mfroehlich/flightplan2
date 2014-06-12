/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring.mbean;

import java.util.Map;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
public interface MethodCallMonitorMXBean {

    public static final String OBJECTNAME = "com.prodyna.pac.flighplan:service=MethodCallMonitor";

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Map<String, Long> getMethodCallCountStatistics();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Map<String, Double> getMethodCallAverageProcessingTimeStatistics();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Map<String, Double> getMethodCallMinProcessingTimeStatistics();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Map<String, Double> getMethodCallMaxProcessingTimeStatistics();

    /**
     * 
     * TODO mfroehlich Comment me
     */
    public void resetStatistics();
}
