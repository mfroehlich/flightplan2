/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class PilotErrorCode extends ErrorCode {

    private static final long serialVersionUID = -9094633278724209764L;

    public static final ErrorCode ID_MUST_NOT_BE_NULL = new PilotErrorCode("id_must_not_be_null");
    public static final ErrorCode PILOT_NOT_FOUND_BY_ID = new PilotErrorCode("pilot_not_found_by_id");
    public static final ErrorCode ERROR_PERSISTING_PILOT = new PilotErrorCode("error_persisting_pilot");
    public static final ErrorCode ERROR_LOADING_ALL_PILOTS = new PilotErrorCode("error_loading_all_pilots");
    public static final ErrorCode ERROR_UPDATING_PILOT = new PilotErrorCode("error_updating_pilot");
    public static final ErrorCode ERROR_DELETING_PILOT = new PilotErrorCode("error_deleting_pilot");
    public static final ErrorCode LICENCE_VALIDITY_INVALID = new PilotErrorCode("licence_validity_invalid");
    public static final ErrorCode AIRCRAFTTYPES_MUST_NOT_BE_NULL = new PilotErrorCode("aircraft_types_must_not_be_null");
    public static final ErrorCode PILOT_CANNOT_BE_DELETED = new PilotErrorCode("pilot_cannot_be_deleted");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected PilotErrorCode() {
    }

    public PilotErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "pilot";
    }
}
