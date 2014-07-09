/**
 * 
 */
package com.prodyna.pac.flightplan.client.aircrafttypeadmin.mainpage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.prodyna.pac.flightplan.client.aircrafttypeadmin.aircrafttypelist.AircraftTypeListPresenter;
import com.prodyna.pac.flightplan.client.aircrafttypeadmin.aircrafttypelist.AircraftTypeListView;
import com.prodyna.pac.flightplan.client.aircrafttypeadmin.details.AircraftTypeDetailsPresenter;
import com.prodyna.pac.flightplan.client.aircrafttypeadmin.details.AircraftTypeDetailsView;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeAdminMainPagePresenter implements Initializable {

    @FXML
    private AnchorPane aircrafttypelistpane;

    @FXML
    private AnchorPane aircrafttypedetailspane;

    private AircraftTypeDetailsPresenter detailsPresenter;
    private AircraftTypeListPresenter listPresenter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AircraftTypeListView listView = new AircraftTypeListView();
        this.listPresenter = (AircraftTypeListPresenter) listView.getPresenter();
        aircrafttypelistpane.getChildren().clear();
        aircrafttypelistpane.getChildren().add(listView.getView());

        AircraftTypeDetailsView detailsView = new AircraftTypeDetailsView();
        this.detailsPresenter = (AircraftTypeDetailsPresenter) detailsView.getPresenter();
        aircrafttypedetailspane.getChildren().clear();
        aircrafttypedetailspane.getChildren().add(detailsView.getView());

        this.detailsPresenter.selectedTypeProperty().bind(this.listPresenter.selectedTypeProperty());
    }

    public void initItems() {
        listPresenter.initItems();
        detailsPresenter.initItems(this);
    }

    public void updateAircraftTypeList() {
        listPresenter.initItems();
    }
}
