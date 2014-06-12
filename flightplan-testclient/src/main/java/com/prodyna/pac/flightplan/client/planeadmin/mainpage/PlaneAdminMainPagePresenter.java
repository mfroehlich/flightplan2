/**
 * 
 */
package com.prodyna.pac.flightplan.client.planeadmin.mainpage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.prodyna.pac.flightplan.client.planeadmin.details.PlaneDetailsPresenter;
import com.prodyna.pac.flightplan.client.planeadmin.details.PlaneDetailsView;
import com.prodyna.pac.flightplan.client.planeadmin.planelist.PlaneListPresenter;
import com.prodyna.pac.flightplan.client.planeadmin.planelist.PlaneListView;

public class PlaneAdminMainPagePresenter implements Initializable {

    @FXML
    private AnchorPane planedetailspane;

    @FXML
    private AnchorPane planelistpane;

    private PlaneDetailsPresenter detailsPresenter;
    private PlaneListPresenter listPresenter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PlaneListView listView = new PlaneListView();
        listPresenter = (PlaneListPresenter) listView.getPresenter();
        planelistpane.getChildren().clear();
        planelistpane.getChildren().add(listView.getView());

        PlaneDetailsView detailsView = new PlaneDetailsView();
        detailsPresenter = (PlaneDetailsPresenter) detailsView.getPresenter();
        planedetailspane.getChildren().clear();
        planedetailspane.getChildren().add(detailsView.getView());

        detailsPresenter.selectedPlaneProperty().bind(listPresenter.selectedPlaneProperty());
    }
}
