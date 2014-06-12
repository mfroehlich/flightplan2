/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.details;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.prodyna.pac.flightplan.client.model.PilotModel;

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
                viewModel.currentPilotProperty().set(newPilot);
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
                }
            }
        });
    }

    public void savePilot() {
        this.viewModel.savePilot();
    }

    public void loadPilot() {
        this.viewModel.loadPilotById("1");
    }

    public void newPilot() {
        this.viewModel.currentPilotProperty().set(new PilotModel());
    }

    public ObjectProperty<PilotModel> selectedPilotProperty() {
        return this.viewModel.selectedPilotProperty();
    }
}
