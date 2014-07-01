/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class PlaneReservationErrorCode extends ErrorCode {

    public static final ErrorCode USER_MAY_NOT_RESERVE_AIRCRAFTTYPE = new PlaneReservationErrorCode(
            "user_may_not_reserve_aircrafttype");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected PlaneReservationErrorCode() {
    }

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
