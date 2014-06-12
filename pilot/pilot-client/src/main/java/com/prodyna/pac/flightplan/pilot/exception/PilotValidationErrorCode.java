/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotValidationErrorCode extends ErrorCode {

    public static final ErrorCode PILOT_ID_NOT_SET = new PilotValidationErrorCode("pilot_id_not_set");

    public PilotValidationErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "pilotvalidation";
    }
}
