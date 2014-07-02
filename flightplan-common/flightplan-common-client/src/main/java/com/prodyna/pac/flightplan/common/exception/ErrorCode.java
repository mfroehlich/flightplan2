/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public abstract class ErrorCode implements Serializable {

    private static final long serialVersionUID = 4740311542415950128L;

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
