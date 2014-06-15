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
public class ApplicationErrorCode extends ErrorCode {

    public static final ErrorCode USER_NOT_AUTHORIZED = new ApplicationErrorCode("user_not_authorized");

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
