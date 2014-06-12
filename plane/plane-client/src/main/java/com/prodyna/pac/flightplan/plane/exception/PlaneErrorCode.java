/**
 * 
 */
package com.prodyna.pac.flightplan.plane.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneErrorCode extends ErrorCode {

    public static final ErrorCode PLANE_NOT_FOUND_BY_ID = new PlaneErrorCode("plane_not_found_by_id");

    public PlaneErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "plane";
    }

}
