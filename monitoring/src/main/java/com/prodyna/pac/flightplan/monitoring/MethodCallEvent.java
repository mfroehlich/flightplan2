/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

/**
 * This event is created by the {@link MethodCallMonitorInterceptor} and sent to the {@link MethodCallEventCollector} in
 * order to update the method call statistics.
 * 
 * @author mfroehlich
 * 
 */
public class MethodCallEvent {

    private final String methodSignature;

    private final long processingTime;

    public MethodCallEvent(String methodSignature, long processingTime) {
        this.methodSignature = methodSignature;
        this.processingTime = processingTime;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public long getProcessingTime() {
        return processingTime;
    }
}
