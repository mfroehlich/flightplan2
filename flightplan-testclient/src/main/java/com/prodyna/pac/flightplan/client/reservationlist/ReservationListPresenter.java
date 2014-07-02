/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationlist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.dialog.ModalDialog;
import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;
import com.prodyna.pac.flightplan.client.reservationdetails.ReservationDetailsPresenter;
import com.prodyna.pac.flightplan.client.reservationdetails.ReservationDetailsView;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationValidationException;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationListPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ReservationListPresenter.class);

    @FXML
    private TableView<PlaneReservationModel> reservationListTable;

    @FXML
    private TableColumn<PlaneReservationModel, String> planeName;
    @FXML
    private TableColumn<PlaneReservationModel, String> numberPlate;
    @FXML
    private TableColumn<PlaneReservationModel, String> startTime;
    @FXML
    private TableColumn<PlaneReservationModel, String> endTime;
    @FXML
    private TableColumn<PlaneReservationModel, String> reservationStatus;

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem deleteReservation;
    @FXML
    private MenuItem editReservation;
    @FXML
    private MenuItem cancelReservation;
    @FXML
    private MenuItem startFlight;
    @FXML
    private MenuItem returnPlane;

    @FXML
    private ReservationListViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.viewModel = new ReservationListViewModel();

        /*
         * Initialize table-cols and table-content.
         */
        planeName.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("planeName"));
        numberPlate.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("planeNumberPlate"));
        startTime.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("endTime"));
        reservationStatus.setCellValueFactory(new PropertyValueFactory<PlaneReservationModel, String>("state"));

        reservationListTable.setItems(FXCollections.observableArrayList());

        viewModel.selectedReservationProperty().bind(reservationListTable.getSelectionModel().selectedItemProperty());

        updateReservationList();
    }

    public void configureContextMenu(WindowEvent event) {

        // TODO mfroehlich Logik auslagern in WorkflowEngine
        ReservationStatus reservationStatus = viewModel.selectedReservationProperty().get().stateProperty().get();
        deleteReservation.disableProperty().set(false);
        editReservation.disableProperty().set(reservationStatus != ReservationStatus.RESERVED);
        cancelReservation.disableProperty().set(reservationStatus != ReservationStatus.RESERVED);
        startFlight.disableProperty().set(reservationStatus != ReservationStatus.RESERVED);
        returnPlane.disableProperty().set(reservationStatus != ReservationStatus.LENT);

    }

    public void updateReservationList() {
        ObservableList<PlaneReservationModel> reservationList = viewModel.loadReservationList();
        reservationListTable.setItems(reservationList);

        PlaneReservationModel selected = viewModel.selectedReservationProperty().get();
        if (reservationList.contains(selected)) {
            reservationListTable.getSelectionModel().select(selected);
        } else {
            reservationListTable.getSelectionModel().selectFirst();
        }
    }

    public void openReservationDetails() {
    }

    public void deleteReservation() throws PlaneReservationValidationException, ReservationValidationException {
        viewModel.deleteSelectedReservation();

        updateReservationList();
    }

    public void editReservation(ActionEvent event) {
        ReservationDetailsView view = new ReservationDetailsView();
        PlaneReservationModel model = viewModel.selectedReservationProperty().get();
        ReservationDetailsPresenter presenter = (ReservationDetailsPresenter) view.getPresenter();
        presenter.initModel(model);

        ModalDialog dialog = new ModalDialog(view.getView());
        dialog.showAndWait();

        updateReservationList();
    }

    public void cancelReservation() throws ReservationWorkflowException {
        viewModel.updateReservationStateToCancelled();

        updateReservationList();
    }

    public void startFlight() throws ReservationWorkflowException {
        viewModel.updateReservationStateToLent();

        updateReservationList();
    }

    public void returnPlane() throws ReservationWorkflowException {
        viewModel.updateReservationStateToReturned();

        updateReservationList();
    }
}
