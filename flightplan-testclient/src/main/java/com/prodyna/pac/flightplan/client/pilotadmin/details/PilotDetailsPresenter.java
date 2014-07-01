/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.details;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.model.PilotModel;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotDetailsPresenter implements Initializable {

    @FXML
    private TextField userid;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private DatePicker licenceValidity;

    @FXML
    private ListView<AircraftTypeModel> assignedTypesListView;

    private PilotDetailsViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new PilotDetailsViewModel();

        /*
         * The user selected a pilot from the list. That pilot data needs to be set as the currently selected pilot in
         * the details view.
         */
        this.viewModel.selectedPilotProperty().addListener(new ChangeListener<PilotModel>() {
            @Override
            public void changed(ObservableValue<? extends PilotModel> arg0, PilotModel oldPilot, PilotModel newPilot) {
                updateCurrentPilot(newPilot);
            }
        });

        /*
         * The current pilot has changed and the connected PilotModel needs to be bound with the GUI components.
         */
        this.viewModel.currentPilotProperty().addListener(new ChangeListener<PilotModel>() {
            @Override
            public void changed(ObservableValue<? extends PilotModel> arg0, PilotModel oldPilot, PilotModel newPilot) {
                if (oldPilot != null) {
                    userid.textProperty().unbindBidirectional(oldPilot.idProperty());
                    username.textProperty().unbindBidirectional(oldPilot.userNameProperty());
                    password.textProperty().unbindBidirectional(oldPilot.passwordProperty());
                    firstname.textProperty().unbindBidirectional(oldPilot.firstNameProperty());
                    lastname.textProperty().unbindBidirectional(oldPilot.lastNameProperty());
                    email.textProperty().unbindBidirectional(oldPilot.emailProperty());
                    licenceValidity.valueProperty().unbindBidirectional(oldPilot.licenceValidityProperty());
                }
                if (newPilot != null) {
                    userid.textProperty().bindBidirectional(newPilot.idProperty());
                    username.textProperty().bindBidirectional(newPilot.userNameProperty());
                    password.textProperty().bindBidirectional(newPilot.passwordProperty());
                    firstname.textProperty().bindBidirectional(newPilot.firstNameProperty());
                    lastname.textProperty().bindBidirectional(newPilot.lastNameProperty());
                    email.textProperty().bindBidirectional(newPilot.emailProperty());
                    licenceValidity.valueProperty().bindBidirectional(newPilot.licenceValidityProperty());

                    updateAssignedTypesSelection();
                }
            }
        });

        password.editableProperty().bind(userid.textProperty().isNull());

        this.assignedTypesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void initItems() {
        ObservableList<AircraftTypeModel> aircraftTypeList = viewModel.loadAllAircraftTypes();
        this.assignedTypesListView.setItems(aircraftTypeList);
    }

    public void savePilot() throws PilotValidationException, UserValidationException {
        if (viewModel.currentPilotProperty().get() != null) {
            ObservableList<AircraftTypeModel> selectedTypeList = assignedTypesListView.getSelectionModel()
                    .getSelectedItems();
            List<AircraftTypeModel> typeList = new ArrayList<>();
            selectedTypeList.forEach(type -> typeList.add(type));
            this.viewModel.currentPilotProperty().get().assignedAircraftTypesProperty().set(typeList);
        }
        this.viewModel.savePilot();
    }

    public void loadPilot() {
        this.viewModel.loadPilotById("1");
        updateAssignedTypesSelection();
    }

    public void newPilot() {
        updateCurrentPilot(new PilotModel());
    }

    public ObjectProperty<PilotModel> selectedPilotProperty() {
        return this.viewModel.selectedPilotProperty();
    }

    private void updateCurrentPilot(PilotModel model) {
        this.viewModel.currentPilotProperty().set(model);
        updateAssignedTypesSelection();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param set
     */
    private void updateAssignedTypesSelection() {
        assignedTypesListView.getSelectionModel().clearSelection();
        List<AircraftTypeModel> assignedTypes = viewModel.currentPilotProperty().get().assignedAircraftTypesProperty()
                .get();
        if (assignedTypes != null) {
            assignedTypes.forEach(type -> assignedTypesListView.getSelectionModel().select(type));
        }
    }
}
