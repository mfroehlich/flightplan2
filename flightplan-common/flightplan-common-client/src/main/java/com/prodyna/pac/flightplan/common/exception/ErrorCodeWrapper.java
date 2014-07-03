/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class to contain List of ErrorCode Strings to be transferred to the client via REST.
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class ErrorCodeWrapper {

    private List<String> errorCodes;

    /**
     * No-args constructor for XML-Marshaller.
     */
    protected ErrorCodeWrapper() {
        this.errorCodes = new ArrayList<String>();
    }

    public ErrorCodeWrapper(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ErrorCodeWrapper(String errorCode) {
        this.errorCodes.add(errorCode);
    }

    @XmlElement
    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<ErrorCode> errorCodes) {
        errorCodes.forEach(code -> this.errorCodes.add(code.getCode()));
    }
}
