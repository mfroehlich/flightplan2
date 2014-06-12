/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationcreation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;
import com.prodyna.pac.flightplan.client.service.PlaneClientService;
import com.prodyna.pac.flightplan.client.service.ReservationClientService;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationCreationViewModel {

    private final BooleanProperty searchConstraintsValid;

    private final ObjectProperty<LocalDate> selectedDate;
    private final ObjectProperty<PlaneModel> selectedPlane;

    // private final ObjectProperty<LocalDateTime> selectedStartTime;
    // private final ObjectProperty<LocalDateTime> selectedEndTime;

    private final ReservationClientService reservationClientService;
    private final PlaneClientService planeClientService;

    // private final BooleanProperty addReservationConstraintsValid;

    public ReservationCreationViewModel() {

        this.searchConstraintsValid = new SimpleBooleanProperty();
        // this.addReservationConstraintsValid = new SimpleBooleanProperty();

        this.selectedDate = new SimpleObjectProperty<>();
        this.selectedPlane = new SimpleObjectProperty<>();
        // this.selectedStartTime = new SimpleObjectProperty<>();
        // this.selectedEndTime = new SimpleObjectProperty<>();

        reservationClientService = new ReservationClientService();
        planeClientService = new PlaneClientService();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<PlaneModel> loadAllPlanes() {
        List<Plane> allPlanes = planeClientService.loadAllPlanes();
        ObservableList<PlaneModel> planeList = FXCollections.observableArrayList();
        allPlanes.forEach(plane -> planeList.add(new PlaneModel(plane)));
        return planeList;
    }

    /**
     * TODO mfroehlich Comment me
     */
    public ObservableList<PlaneReservationModel> loadReservations() {
        Plane selectedPlane = this.selectedPlane.get().getEntity();
        LocalDate selectedLocalDate = this.selectedDate.get();
        Date selectedDate = LocalDateConverter.localDateToDate(selectedLocalDate);

        List<PlaneReservation> reservationList = reservationClientService.loadReservations(selectedPlane, selectedDate);
        ObservableList<PlaneReservationModel> planeReservationList = FXCollections.observableArrayList();
        reservationList.forEach(res -> planeReservationList.add(new PlaneReservationModel(res)));

        return planeReservationList;
    }

    public BooleanProperty searchConstraintsValidProperty() {
        return searchConstraintsValid;
    }

    public boolean isSearchConstraintsValid() {
        return searchConstraintsValid.get();
    }

    public ObjectProperty<LocalDate> selectedDateProperty() {
        return this.selectedDate;
    }

    public ObjectProperty<PlaneModel> selectedPlaneProperty() {
        return this.selectedPlane;
    }

    // public ObjectProperty<LocalDateTime> selectedStartTimeProperty() {
    // return this.selectedStartTime;
    // }
    //
    // public ObjectProperty<LocalDateTime> selectedEndTimeProperty() {
    // return this.selectedEndTime;
    // }

    // /**
    // * TODO mfroehlich Comment me
    // *
    // * @return
    // */
    // public BooleanProperty addReservationConstraintsValidProperty() {
    // return addReservationConstraintsValid;
    // }
}
