/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

/**
 * Statistics object for method calls that is held in a map by the {@link MethodCallEventCollector}.
 * 
 * @author mfroehlich
 * 
 */
public class MethodCallStatistics {

    private long callCounter;

    private Long minProcessingTime;

    private Long maxProcessingTime;

    private Long totalProcessingTime;

    /**
     * TODO mfroehlich Comment me
     * 
     * @param event
     */
    public void processEvent(MethodCallEvent event) {

        long processingTime = event.getProcessingTime();

        callCounter++;

        if (totalProcessingTime == null) {
            totalProcessingTime = processingTime;
        } else {
            totalProcessingTime += processingTime;
        }

        if (minProcessingTime == null || minProcessingTime > processingTime) {
            minProcessingTime = processingTime;
        }

        if (maxProcessingTime == null || maxProcessingTime < processingTime) {
            maxProcessingTime = processingTime;
        }
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public long getCallCounter() {
        return callCounter;
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public double getAvgProcessingTime() {

        double avgProcessingTime;
        if (totalProcessingTime == null) {
            avgProcessingTime = 0;
        } else {
            avgProcessingTime = totalProcessingTime / callCounter;
        }

        return avgProcessingTime;
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public double getMinProcessingTime() {
        return this.minProcessingTime;
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public double getMaxProcessingTime() {

        return this.maxProcessingTime;
    }
}
