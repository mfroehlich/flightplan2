/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneReservationErrorCode extends ErrorCode {

    public static final ErrorCode USER_MAY_NOT_RESERVE_AIRCRAFTTYPE = new PlaneReservationErrorCode(
            "user_may_not_reserve_aircrafttype");

    /**
     * @param code
     */
    public PlaneReservationErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "planereservation";
    }

}
