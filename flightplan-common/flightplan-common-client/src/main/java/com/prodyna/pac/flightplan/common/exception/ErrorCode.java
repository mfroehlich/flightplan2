/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public abstract class ErrorCode {

    private final String code;

    public ErrorCode(String code) {
        this.code = code;
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    protected abstract String getPrefix();

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public String getCode() {
        return getPrefix() + "." + code;
    }
}
