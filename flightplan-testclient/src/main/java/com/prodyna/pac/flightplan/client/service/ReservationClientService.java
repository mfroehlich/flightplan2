/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.session.SessionManager;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.service.PlaneReservationService;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;
import com.prodyna.pac.flightplan.reservation.service.ReservationWorkflowService;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationClientService extends AbstractClientService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationClientService.class);

    private final ReservationService reservationService;
    private final ReservationWorkflowService reservationWorkflowService;
    private final PlaneReservationService planeReservationService;

    public ReservationClientService() {
        this.reservationService = createRestService(ReservationService.class);
        this.reservationWorkflowService = createRestService(ReservationWorkflowService.class);
        this.planeReservationService = createRestService(PlaneReservationService.class);
    }

    public void createReservation(Reservation reservation) {
        logger.debug("Calling REST service to create the following reservation " + reservation);
        reservationService.createReservation(reservation);
    }

    public List<PlaneReservation> loadReservations(Plane selectedPlane, Date selectedDate) {
        String id = selectedPlane.getId();
        logger.debug("Calling REST service to load all reservations for planeId '" + id + "' and date '" + selectedDate
                + "'.");
        List<PlaneReservation> reservationList = planeReservationService.loadPlaneReservationsByPlaneAndDate(id,
                selectedDate.getTime());
        logger.debug("Found " + reservationList.size() + " reservations for planeId '" + id + "' and date '"
                + selectedDate + "'.");
        return reservationList;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public List<PlaneReservation> loadReservationsForLoggedInUser() {
        logger.debug("Calling REST service to load all reservations assigned to the logged in user.");
        String userId = SessionManager.getInstance().getLoggedInUser().getId();
        List<PlaneReservation> reservationList = planeReservationService.loadPlaneReservationsByUserId(userId);
        logger.debug("Found " + reservationList.size() + " reservations assigned to the user with id " + userId);
        return reservationList;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservation
     */
    public void updateReservation(Reservation reservation) {
        logger.debug("Calling REST service to update reservation " + reservation);
        reservationService.updateReservation(reservation);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservationId
     */
    public void deleteReservationById(String reservationId) {
        logger.debug("Calling REST service to delete reservation by id " + reservationId);
        reservationService.deleteReservationById(reservationId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservationId
     */
    public void cancelReservation(String reservationId) {
        logger.debug("Calling REST service to cancel reservation by id " + reservationId);
        reservationWorkflowService.cancelReservation(reservationId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservationId
     */
    public void receiveReservationItem(String reservationId) {
        logger.debug("Calling REST service to receive reservation item for reservation by id " + reservationId);
        reservationWorkflowService.receiveReservationItemWithReservationId(reservationId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservationId
     */
    public void returnReservationItem(String reservationId) {
        logger.debug("Calling REST service to return reservation item for reservation by id " + reservationId);
        reservationWorkflowService.returnReservationItemWithReservationId(reservationId);
    }
}
