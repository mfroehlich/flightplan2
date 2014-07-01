/**
 * 
 */
package com.prodyna.pac.flightplan.client.planeadmin.planelist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneListPresenter implements Initializable {

    @FXML
    private ListView<PlaneModel> planeListView;

    private PlaneListViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel = new PlaneListViewModel();
        loadPlanes();
    }

    public void deletePlane() throws PlaneValidationException {
        PlaneModel selectedPlane = planeListView.getSelectionModel().getSelectedItem();
        if (selectedPlane != null) {
            String planeId = selectedPlane.idProperty().get();
            viewModel.deletePlaneById(planeId);
        }
        loadPlanes();
    }

    public void loadPlanes() {
        ObservableList<PlaneModel> planeList = viewModel.loadPlaneList();
        planeListView.setItems(planeList);
        planeListView.getSelectionModel().selectFirst();
    }

    public ReadOnlyObjectProperty<PlaneModel> selectedPlaneProperty() {
        return planeListView.getSelectionModel().selectedItemProperty();
    }
}
