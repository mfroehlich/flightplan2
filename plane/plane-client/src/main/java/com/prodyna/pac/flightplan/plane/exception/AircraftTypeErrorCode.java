/**
 * 
 */
package com.prodyna.pac.flightplan.plane.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;

/**
 * Error codes describing functional and technical errors that might occur when working with {@link AircraftType}
 * objects.
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class AircraftTypeErrorCode extends ErrorCode {

    public static final ErrorCode UNKNOWN_ERROR = new AircraftTypeErrorCode("unknown_error");
    public static final ErrorCode ID_NOT_SET = new AircraftTypeErrorCode("aircraft_type_id_not_set");
    public static final ErrorCode AIRCRAFTTYPE_REFERENCED_BY_PLANES = new AircraftTypeErrorCode(
            "aircraft_type_referenced_by_planes");
    public static final ErrorCode DESCRIPTION_IS_NOT_SET = new AircraftTypeErrorCode("description_is_not_set");
    public static final ErrorCode DESCRIPTION_NOT_UNIQUE = new AircraftTypeErrorCode("description_not_unique");
    public static final ErrorCode AIRCRAFTTYPE_TO_BE_DELETED_NOT_FOUND = new AircraftTypeErrorCode(
            "aircraft_type_to_be_deleted_not_found");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected AircraftTypeErrorCode() {
    }

    public AircraftTypeErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "aircraft_type";
    }

}
