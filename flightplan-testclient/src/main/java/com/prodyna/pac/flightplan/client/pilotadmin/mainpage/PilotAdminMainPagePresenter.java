/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.mainpage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.prodyna.pac.flightplan.client.pilotadmin.details.PilotDetailsPresenter;
import com.prodyna.pac.flightplan.client.pilotadmin.details.PilotDetailsView;
import com.prodyna.pac.flightplan.client.pilotadmin.pilotlist.PilotListPresenter;
import com.prodyna.pac.flightplan.client.pilotadmin.pilotlist.PilotListView;

public class PilotAdminMainPagePresenter implements Initializable {

    @FXML
    private AnchorPane pilotdetailspane;

    @FXML
    private AnchorPane pilotlistpane;

    private PilotDetailsPresenter detailsPresenter;
    private PilotListPresenter listPresenter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PilotListView listView = new PilotListView();
        listPresenter = (PilotListPresenter) listView.getPresenter();
        pilotlistpane.getChildren().clear();
        pilotlistpane.getChildren().add(listView.getView());

        PilotDetailsView detailsView = new PilotDetailsView();
        detailsPresenter = (PilotDetailsPresenter) detailsView.getPresenter();
        pilotdetailspane.getChildren().clear();
        pilotdetailspane.getChildren().add(detailsView.getView());

        detailsPresenter.selectedPilotProperty().bind(listPresenter.selectedPilotProperty());
    }
}
