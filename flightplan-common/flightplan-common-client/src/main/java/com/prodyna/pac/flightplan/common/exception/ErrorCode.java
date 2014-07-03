/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This abstract class and implementations of this class are needed to represent error situations.
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
     * Prefix string to bundle a group of similar errors.
     * 
     * @return
     */
    protected abstract String getPrefix();

    /**
     * 
     * String representation of a concrete error (error code).
     * 
     * @return
     */
    @XmlElement
    public String getCode() {
        return getPrefix() + "." + code;
    }

    @Override
    public String toString() {
        return getCode();
    }
}
