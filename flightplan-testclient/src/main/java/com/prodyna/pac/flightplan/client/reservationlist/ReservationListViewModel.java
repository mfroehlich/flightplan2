/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationlist;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;
import com.prodyna.pac.flightplan.client.service.ReservationClientService;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationListViewModel {

    private final ObjectProperty<PlaneReservationModel> selectedReservation;

    private final ReservationClientService reservationService;

    public ReservationListViewModel() {
        selectedReservation = new SimpleObjectProperty<>();
        reservationService = new ReservationClientService();
    }

    /**
     * TODO mfroehlich Comment me
     */
    public void deleteSelectedReservation() {
        String reservationId = selectedReservation.get().idProperty().get();
        reservationService.deleteReservationById(reservationId);
    }

    public ObjectProperty<PlaneReservationModel> selectedReservationProperty() {
        return this.selectedReservation;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<PlaneReservationModel> loadReservationList() {
        List<PlaneReservation> resList = reservationService.loadReservationsForLoggedInUser();
        ObservableList<PlaneReservationModel> planeReservations = FXCollections.observableArrayList();
        resList.forEach(res -> planeReservations.add(new PlaneReservationModel(res)));
        return planeReservations;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @throws ReservationWorkflowException
     */
    public void updateReservationStateToCancelled() throws ReservationWorkflowException {
        String reservationId = selectedReservation.get().idProperty().get();
        reservationService.cancelReservation(reservationId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @throws ReservationWorkflowException
     */
    public void updateReservationStateToLent() throws ReservationWorkflowException {
        String reservationId = selectedReservation.get().idProperty().get();
        reservationService.receiveReservationItem(reservationId);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @throws ReservationWorkflowException
     */
    public void updateReservationStateToReturned() throws ReservationWorkflowException {
        String reservationId = selectedReservation.get().idProperty().get();
        reservationService.returnReservationItem(reservationId);
    }
}
