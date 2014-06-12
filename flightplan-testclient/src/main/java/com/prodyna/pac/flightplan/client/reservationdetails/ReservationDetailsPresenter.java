/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationdetails;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationDetailsPresenter implements Initializable {

    private ReservationDetailsViewModel viewModel;

    @FXML
    private TextField pilotName;

    @FXML
    private ChoiceBox<PlaneModel> planeSelect;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private TextField startHour;
    @FXML
    private TextField startMinute;
    @FXML
    private TextField endHour;
    @FXML
    private TextField endMinute;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new ReservationDetailsViewModel();

        pilotName.textProperty().bind(viewModel.pilotNameProperty());
        planeSelect.valueProperty().bindBidirectional(viewModel.selectedPlaneProperty());
        dateSelect.valueProperty().bindBidirectional(viewModel.selectedDateProperty());
        startHour.textProperty().bindBidirectional(viewModel.startHourProperty());
        startMinute.textProperty().bindBidirectional(viewModel.startMinuteProperty());
        endHour.textProperty().bindBidirectional(viewModel.endHourProperty());
        endMinute.textProperty().bindBidirectional(viewModel.endMinuteProperty());

        initPlaneList();
    }

    /**
     * 
     * TODO mfroehlich Comment me
     * 
     * @param model
     */
    public void initModel(PlaneReservationModel model) {
        viewModel.initModel(model);
    }

    /**
     * TODO mfroehlich Comment me
     */
    public void initModel(PlaneModel plane, LocalDate selectedDate) {
        viewModel.initModel(plane, selectedDate);
    }

    /**
     * Create or update the reservation represented in the data model. TODO mfroehlich Comment me
     * 
     * @param event
     */
    public void saveReservation(ActionEvent event) {
        viewModel.createReservation();

        /* Close the dialog. */
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public ObjectProperty<PlaneModel> planeSelectProperty() {
        return viewModel.selectedPlaneProperty();
    }

    public ObjectProperty<LocalDate> dateSelectProperty() {
        return viewModel.selectedDateProperty();
    }

    private void initPlaneList() {
        ObservableList<PlaneModel> loadAllPlanes = viewModel.loadAllPlanes();
        planeSelect.setItems(loadAllPlanes);
    }
}
