/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
public class ReservationErrorCode extends ErrorCode {

    private static final long serialVersionUID = -919900881044191391L;

    public static final ErrorCode ERROR_PERSISTING_RESERVATION = new ReservationErrorCode(
            "error_persisting_reservation");
    public static final ErrorCode ERROR_LOADING_RESERVATION_BY_ID = new ReservationErrorCode(
            "error_loading_reservation_by_id");
    public static final ErrorCode ERROR_UPDATING_RESERVATION = new ReservationErrorCode("error_updating_reservation");
    public static final ErrorCode ERROR_DELETING_RESERVATION = new ReservationErrorCode("error_deleting_reservation");

    public static final ErrorCode UNKNOWN_ERROR = new ReservationErrorCode("unknown_error");
    public static final ErrorCode ID_MUST_NOT_BE_NULL = new ReservationErrorCode("id_must_not_be_null");
    public static final ErrorCode STARTTIME_MUST_NOT_BE_NULL = new ReservationErrorCode("starttime_must_not_be_null");
    public static final ErrorCode ENDTIME_MUST_NOT_BE_NULL = new ReservationErrorCode("endtime_must_not_be_null");
    public static final ErrorCode STARTTIME_MUST_BE_BEFORE_ENDTIME = new ReservationErrorCode(
            "starttime_must_be_before_endtime");
    public static final ErrorCode STATUS_MUST_NOT_BE_NULL = new ReservationErrorCode("status_must_not_be_null");
    public static final ErrorCode USER_MUST_NOT_BE_NULL = new ReservationErrorCode("user_must_not_be_null");
    public static final ErrorCode RESERVATION_ITEM_MUST_NOT_BE_NULL = new ReservationErrorCode(
            "reservation_item_must_not_be_null");
    public static final ErrorCode RESERVATION_TO_BE_DELETED_DOES_NOT_EXIST = new ReservationErrorCode(
            "reservation_to_be_deleted_does_not_exist");

    /**
     * This error occurs when trying to change the {@link ReservationStatus} for a {@link Reservation} which has the
     * wrong {@link ReservationStatus}, e.g. canceling an already returned reservation is no valid action.
     */
    public static final ErrorCode WRONG_RESERVATION_STATUS = new ReservationErrorCode("wrong_reservation_status");
    public static final ErrorCode RESERVATION_DATE_IN_PAST_OR_FUTURE = new ReservationErrorCode(
            "future_reservation_cannot_be_received");
    public static final ErrorCode RESERVATION_CONFLICT = new ReservationErrorCode("reservation_conflict");

    /**
     * Default (protected!) constructor for Marshaller.
     */
    protected ReservationErrorCode() {
    }

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
