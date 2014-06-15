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
public class PilotErrorCode extends ErrorCode {

    public static final ErrorCode PILOT_NOT_FOUND_BY_ID = new PilotErrorCode("pilot_not_found_by_id");
    public static final ErrorCode ERROR_PERSISTING_PILOT = new PilotErrorCode("error_persisting_pilot");
    public static final ErrorCode ERROR_LOADING_ALL_PILOTS = new PilotErrorCode("error_loading_all_pilots");
    public static final ErrorCode ERROR_UPDATING_PILOT = new PilotErrorCode("error_updating_pilot");
    public static final ErrorCode ERROR_DELETING_PILOT = new PilotErrorCode("error_deleting_pilot");

    public static final ErrorCode PILOT_ID_NOT_SET = new PilotErrorCode("pilot_id_not_set");

    public PilotErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "pilot";
    }
}
