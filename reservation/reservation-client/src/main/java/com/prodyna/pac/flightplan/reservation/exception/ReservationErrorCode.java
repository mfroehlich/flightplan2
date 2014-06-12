/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationErrorCode extends ErrorCode {

    public static final ErrorCode ERROR_PERSISTING_RESERVATION = new ReservationErrorCode(
            "error_persisting_reservation");
    public static final ErrorCode ERROR_LOADING_RESERVATION_BY_ID = new ReservationErrorCode(
            "error_loading_reservation_by_id");
    public static final ErrorCode ERROR_UPDATING_RESERVATION = new ReservationErrorCode("error_updating_reservation");
    public static final ErrorCode ERROR_DELETING_RESERVATION = new ReservationErrorCode("error_deleting_reservation");

    /**
     * This error occurs when trying to change the {@link ReservationStatus} for a {@link Reservation} which has the
     * wrong {@link ReservationStatus}, e.g. canceling an already returned reservation is no valid action.
     */
    public static final ErrorCode WRONG_RESERVATION_STATUS = new ReservationErrorCode("wrong_reservation_status");

    /**
     * @param code
     */
    public ReservationErrorCode(String code) {
        super(code);
    }

    @Override
    protected String getPrefix() {
        return "reservation";
    }

}
