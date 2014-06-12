/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring.mbean;

import java.util.HashMap;
import java.util.Map;

import com.prodyna.pac.flightplan.monitoring.MethodCallEventCollector;
import com.prodyna.pac.flightplan.monitoring.MethodCallStatistics;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
public class MethodCallMonitor implements MethodCallMonitorMXBean {

    private final MethodCallEventCollector collector;

    public MethodCallMonitor(MethodCallEventCollector collector) {
        this.collector = collector;
    }

    @Override
    public Map<String, Long> getMethodCallCountStatistics() {

        Map<String, MethodCallStatistics> statMap = collector.getMethodCallStatisticsMap();
        Map<String, Long> result = new HashMap<String, Long>();
        for (String methodSig : statMap.keySet()) {
            result.put(methodSig, statMap.get(methodSig).getCallCounter());
        }

        return result;
    }

    @Override
    public Map<String, Double> getMethodCallAverageProcessingTimeStatistics() {

        Map<String, MethodCallStatistics> statMap = collector.getMethodCallStatisticsMap();
        Map<String, Double> result = new HashMap<String, Double>();
        for (String methodSig : statMap.keySet()) {
            result.put(methodSig, statMap.get(methodSig).getAvgProcessingTime());
        }

        return result;
    }

    @Override
    public Map<String, Double> getMethodCallMinProcessingTimeStatistics() {
        Map<String, MethodCallStatistics> statMap = collector.getMethodCallStatisticsMap();
        Map<String, Double> result = new HashMap<String, Double>();
        for (String methodSig : statMap.keySet()) {
            result.put(methodSig, statMap.get(methodSig).getMinProcessingTime());
        }

        return result;
    }

    @Override
    public Map<String, Double> getMethodCallMaxProcessingTimeStatistics() {
        Map<String, MethodCallStatistics> statMap = collector.getMethodCallStatisticsMap();
        Map<String, Double> result = new HashMap<String, Double>();
        for (String methodSig : statMap.keySet()) {
            result.put(methodSig, statMap.get(methodSig).getMaxProcessingTime());
        }

        return result;
    }

    @Override
    public void resetStatistics() {
        collector.reset();
    }
}
