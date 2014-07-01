/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public abstract class ErrorCode {

    private String code;

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected ErrorCode() {
    }

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
