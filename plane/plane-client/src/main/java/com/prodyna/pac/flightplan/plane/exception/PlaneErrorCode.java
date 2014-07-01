/**
 * 
 */
package com.prodyna.pac.flightplan.plane.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class PlaneErrorCode extends ErrorCode {

    public static final ErrorCode PLANE_NOT_FOUND_BY_ID = new PlaneErrorCode("plane_not_found_by_id");
    public static final ErrorCode ID_MUST_NOT_BE_NULL = new PlaneErrorCode("id_must_not_be_null");
    public static final ErrorCode NAME_INVALID = new PlaneErrorCode("name_must_not_be_null");
    public static final ErrorCode NUMBERPLATE_INVALID = new PlaneErrorCode("numberplate_must_not_be_null");
    public static final ErrorCode AIRCRAFTTYPE_MUST_NOT_BE_NULL = new PlaneErrorCode("aircrafttype_must_not_be_null");
    public static final ErrorCode UNKNOWN_ERROR = new PlaneErrorCode("unknown_error");
    public static final ErrorCode NAME_AND_NUMBERPLATE_MUST_BE_UNIQUE = new PlaneErrorCode(
            "name_and_numberplate_must_be_unique");
    public static final ErrorCode PLANE_CANNOT_BE_DELETED = new PlaneErrorCode("plane_cannot_be_deleted");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected PlaneErrorCode() {
    }

    public PlaneErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "plane";
    }

}
