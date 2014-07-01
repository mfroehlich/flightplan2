/**
 * 
 */
package com.prodyna.pac.flightplan.client.aircrafttypeadmin.aircrafttypelist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeListPresenter implements Initializable {

    @FXML
    private ListView<AircraftTypeModel> listView;

    private AircraftTypeListViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new AircraftTypeListViewModel();

        this.viewModel.selectedAircraftTypeProperty().bind(selectedTypeProperty());

        initAircraftTypeList();
    }

    public void initAircraftTypeList() {
        ObservableList<AircraftTypeModel> items = viewModel.loadAllAircraftTypes();
        listView.setItems(items);
        listView.getSelectionModel().selectFirst();
    }

    public void deleteAircraftType() throws AircraftTypeValidationException {
        this.viewModel.deleteAircraftType();
        initAircraftTypeList();
    }

    public ReadOnlyObjectProperty<AircraftTypeModel> selectedTypeProperty() {
        return listView.getSelectionModel().selectedItemProperty();
    }
}
