/**
 * 
 */
package com.prodyna.pac.flightplan.client.aircrafttypeadmin.details;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import com.prodyna.pac.flightplan.client.aircrafttypeadmin.mainpage.AircraftTypeAdminMainPagePresenter;
import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeDetailsPresenter implements Initializable {

    @FXML
    private TextField typeid;

    @FXML
    private TextField description;

    private AircraftTypeDetailsViewModel viewModel;

    private AircraftTypeAdminMainPagePresenter parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new AircraftTypeDetailsViewModel();

        viewModel.selectedTypeProperty().addListener(new ChangeListener<AircraftTypeModel>() {
            @Override
            public void changed(ObservableValue<? extends AircraftTypeModel> arg0, AircraftTypeModel oldType,
                    AircraftTypeModel newType) {
                viewModel.currentTypeProperty().set(newType);
            }
        });

        viewModel.currentTypeProperty().addListener(new ChangeListener<AircraftTypeModel>() {
            @Override
            public void changed(ObservableValue<? extends AircraftTypeModel> observable, AircraftTypeModel oldValue,
                    AircraftTypeModel newValue) {
                if (oldValue != null) {
                    typeid.textProperty().unbindBidirectional(oldValue.idProperty());
                    description.textProperty().unbindBidirectional(oldValue.descriptionProperty());
                }
                if (newValue != null) {
                    typeid.textProperty().bindBidirectional(newValue.idProperty());
                    description.textProperty().bindBidirectional(newValue.descriptionProperty());
                }
            }
        });
    }

    public void initItems(AircraftTypeAdminMainPagePresenter parent) {
        this.parent = parent;
    }

    public void saveAircraftType() throws AircraftTypeValidationException {
        this.viewModel.saveAircraftType();
        parent.updateAircraftTypeList();
    }

    public void loadAircraftType() {
        this.viewModel.loadAircraftTypeById("1");
    }

    public void newAircraftType() {
        this.viewModel.newAircraftType();
    }

    public ObjectProperty<AircraftTypeModel> selectedTypeProperty() {
        return viewModel.selectedTypeProperty();
    }
}
