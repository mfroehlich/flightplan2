/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.pilotlist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import com.prodyna.pac.flightplan.client.changepassword.ChangePasswordPresenter;
import com.prodyna.pac.flightplan.client.changepassword.ChangePasswordView;
import com.prodyna.pac.flightplan.client.dialog.ModalDialog;
import com.prodyna.pac.flightplan.client.model.PilotModel;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotListPresenter implements Initializable {

    @FXML
    private ListView<PilotModel> pilotListView;

    private PilotListViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel = new PilotListViewModel();
    }

    public void initItems() {
        loadPilots();
    }

    public void deletePilot() {
        PilotModel selectedPilot = pilotListView.getSelectionModel().getSelectedItem();
        if (selectedPilot != null) {
            String pilotId = selectedPilot.idProperty().get();
            viewModel.deletePilotById(pilotId);
        }

        loadPilots();
    }

    public void changePassword() {
        ChangePasswordView view = new ChangePasswordView();
        String userId = pilotListView.getSelectionModel().getSelectedItem().idProperty().get();
        ChangePasswordPresenter presenter = (ChangePasswordPresenter) view.getPresenter();
        presenter.initItems(userId);

        ModalDialog dialog = new ModalDialog(view.getView());
        dialog.showAndWait();
    }

    public void loadPilots() {
        ObservableList<PilotModel> pilotList = viewModel.loadPilotList();
        pilotListView.setItems(pilotList);
        pilotListView.getSelectionModel().selectFirst();
    }

    public ReadOnlyObjectProperty<PilotModel> selectedPilotProperty() {
        return pilotListView.getSelectionModel().selectedItemProperty();
    }
}
