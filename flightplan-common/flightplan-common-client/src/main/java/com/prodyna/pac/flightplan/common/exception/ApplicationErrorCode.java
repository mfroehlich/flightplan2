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
public class ApplicationErrorCode extends ErrorCode {

    public static final ErrorCode USER_NOT_AUTHORIZED = new ApplicationErrorCode("user_not_authorized");
    public static final ErrorCode OPTIMISTIC_LOCK_EXCEPTION = new ApplicationErrorCode("optimistic_lock_exception");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected ApplicationErrorCode() {
    }

    /**
     * @param code
     */
    public ApplicationErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "application";
    }

}
