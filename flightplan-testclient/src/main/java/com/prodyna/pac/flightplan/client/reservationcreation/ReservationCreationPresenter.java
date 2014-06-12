/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationcreation;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.dialog.ModalDialog;
import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;
import com.prodyna.pac.flightplan.client.reservationdetails.ReservationDetailsPresenter;
import com.prodyna.pac.flightplan.client.reservationdetails.ReservationDetailsView;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationCreationPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ReservationCreationPresenter.class);

    @FXML
    private ChoiceBox<PlaneModel> planeSelect;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<PlaneReservationModel> reservationList;
    @FXML
    private TableColumn<PlaneReservationModel, String> userName;
    @FXML
    private TableColumn<PlaneReservationModel, String> planeName;
    @FXML
    private TableColumn<PlaneReservationModel, String> planeNumberPlate;
    @FXML
    private TableColumn<PlaneReservationModel, String> startDate;
    @FXML
    private TableColumn<PlaneReservationModel, String> endDate;
    @FXML
    private TableColumn<PlaneReservationModel, String> state;

    @FXML
    private Button addReservationButton;

    private ReservationCreationViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new ReservationCreationViewModel();

        /*
         * Initialize table-cols and table-content.
         */
        userName.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("pilotName"));
        planeName.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("planeName"));
        planeNumberPlate
                .setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("planeNumberPlate"));
        startDate.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("startTime"));
        endDate.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("endTime"));
        state.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("state"));

        reservationList.setItems(FXCollections.observableArrayList());

        viewModel.selectedPlaneProperty().bind(planeSelect.getSelectionModel().selectedItemProperty());
        viewModel.selectedDateProperty().bind(datePicker.valueProperty());

        /*
         * Define the constraints the selected search values must meet in order to be valid to initiate the search for
         * reservations via REST.
         */
        viewModel.searchConstraintsValidProperty().bind(
                Bindings.isNotNull(viewModel.selectedDateProperty()).and(
                        Bindings.isNotNull(viewModel.selectedPlaneProperty())));

        /*
         * Add listeners on components that must be selected before reservations will be loaded via REST.
         */
        viewModel.selectedPlaneProperty().addListener(new ChangeListener<PlaneModel>() {
            @Override
            public void changed(ObservableValue<? extends PlaneModel> observable, PlaneModel oldValue,
                    PlaneModel newValue) {
                updateReservationList();
            }
        });
        viewModel.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                updateReservationList();
            }
        });
    }

    public void initItems() {
        ObservableList<PlaneModel> planeList = viewModel.loadAllPlanes();
        planeSelect.setItems(planeList);
        datePicker.setValue(LocalDate.now());
    }

    public void openReservationCreationDialog(ActionEvent event) {
        ReservationDetailsView view = new ReservationDetailsView();
        ReservationDetailsPresenter presenter = (ReservationDetailsPresenter) view.getPresenter();
        presenter.initModel(planeSelect.valueProperty().get(), datePicker.valueProperty().get());

        ModalDialog dialog = new ModalDialog(view.getView());
        dialog.showAndWait();

        updateReservationList();
    }

    public void updateReservationList() {
        if (viewModel.isSearchConstraintsValid() == true) {
            logger.debug("Search constraints are valid. Searching reservations for specified search values.");
            ObservableList<PlaneReservationModel> planeReservationList = viewModel.loadReservations();
            reservationList.setItems(planeReservationList);
            logger.debug("Found " + planeReservationList.size() + " reservations to be displayed.");
        } else {
            logger.debug("Search constraints are not valid to initiate a search for reservations.");
        }
    }
}
