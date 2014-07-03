/**
 * 
 */
package com.prodyna.pac.flightplan.client.planeadmin.details;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.planeadmin.mainpage.PlaneAdminMainPagePresenter;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneDetailsPresenter implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(PlaneDetailsPresenter.class);

    @FXML
    private TextField planeId;

    @FXML
    private TextField name;

    @FXML
    private TextField numberPlate;

    @FXML
    private ChoiceBox<AircraftTypeModel> aircraftType;

    private PlaneDetailsViewModel viewModel;

    private PlaneAdminMainPagePresenter parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new PlaneDetailsViewModel();

        this.selectedPlaneProperty().addListener(new ChangeListener<PlaneModel>() {
            @Override
            public void changed(ObservableValue<? extends PlaneModel> observable, PlaneModel oldValue,
                    PlaneModel newValue) {
                logger.debug("Selected value changed: " + newValue);
                viewModel.currentPlaneProperty().set(newValue);
            }
        });

        this.viewModel.currentPlaneProperty().addListener(new ChangeListener<PlaneModel>() {
            @Override
            public void changed(ObservableValue<? extends PlaneModel> observable, PlaneModel oldValue,
                    PlaneModel newValue) {

                logger.debug("Current value changed: " + newValue);

                if (oldValue != null) {
                    logger.debug("Unbinding oldValue " + oldValue);
                    planeId.textProperty().unbindBidirectional(oldValue.idProperty());
                    name.textProperty().unbindBidirectional(oldValue.nameProperty());
                    numberPlate.textProperty().unbindBidirectional(oldValue.numberPlateProperty());
                    aircraftType.valueProperty().unbindBidirectional(oldValue.aircraftTypeProperty());
                }
                if (newValue != null) {
                    logger.debug("Binding newValue " + newValue);
                    planeId.textProperty().bindBidirectional(newValue.idProperty());
                    name.textProperty().bindBidirectional(newValue.nameProperty());
                    numberPlate.textProperty().bindBidirectional(newValue.numberPlateProperty());
                    aircraftType.valueProperty().bindBidirectional(newValue.aircraftTypeProperty());
                }
            }
        });
    }

    public void initItems(PlaneAdminMainPagePresenter parent) {
        this.parent = parent;

        ObservableList<AircraftTypeModel> aircraftTypeList = viewModel.loadAircraftTypes();
        this.aircraftType.setItems(aircraftTypeList);
    }

    public void newPlane() {
        this.viewModel.currentPlaneProperty().set(new PlaneModel());
    }

    public void savePlane() throws PlaneValidationException {
        this.viewModel.savePlane();
        parent.updatePlaneList();
    }

    public void loadPlane() {
        this.viewModel.loadPlaneById("1");
    }

    public ObjectProperty<PlaneModel> selectedPlaneProperty() {
        return viewModel.selectedPlaneProperty();
    }
}
