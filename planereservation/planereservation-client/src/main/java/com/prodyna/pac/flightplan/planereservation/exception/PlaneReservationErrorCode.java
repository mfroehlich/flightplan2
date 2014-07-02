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

    private static final long serialVersionUID = -3261268640513831846L;

    public static final ErrorCode USER_MAY_NOT_RESERVE_AIRCRAFTTYPE = new PlaneReservationErrorCode(
            "user_may_not_reserve_aircrafttype");
    public static final ErrorCode PILOT_MAY_NOT_BE_NULL = new PlaneReservationErrorCode("pilot_may_not_be_null");
    public static final ErrorCode PLANE_MAY_NOT_BE_NULL = new PlaneReservationErrorCode("plane_may_not_be_null");
    public static final ErrorCode ID_MAY_NOT_BE_NULL = new PlaneReservationErrorCode("id_may_not_be_null");
    public static final ErrorCode RESERVATION_CANNOT_BE_DELETED = new PlaneReservationErrorCode(
            "reservation_cannot_be_deleted");

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
