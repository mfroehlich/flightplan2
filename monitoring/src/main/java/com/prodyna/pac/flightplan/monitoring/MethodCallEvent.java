/**
 * 
 */
package com.prodyna.pac.flightplan.monitoring;

/**
 * TODO mfroehlich Comment me
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
