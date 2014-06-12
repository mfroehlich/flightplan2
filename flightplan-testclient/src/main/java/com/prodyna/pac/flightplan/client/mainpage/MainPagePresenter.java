package com.prodyna.pac.flightplan.client.mainpage;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.aircrafttypeadmin.mainpage.AircraftTypeAdminMainPageView;
import com.prodyna.pac.flightplan.client.pilotadmin.mainpage.PilotAdminMainPageView;
import com.prodyna.pac.flightplan.client.planeadmin.mainpage.PlaneAdminMainPageView;
import com.prodyna.pac.flightplan.client.reservationcreation.ReservationCreationPresenter;
import com.prodyna.pac.flightplan.client.reservationcreation.ReservationCreationView;
import com.prodyna.pac.flightplan.client.reservationlist.ReservationListView;
import com.prodyna.pac.flightplan.client.session.SessionManager;

public class MainPagePresenter {

    private static final Logger logger = LoggerFactory.getLogger(MainPagePresenter.class);

    @FXML
    private Pane mainScene;

    @FXML
    private Label statusBarLabel;

    ReservationCreationPresenter reservationCreationPresenter;

    public void openScenePilotAdministration() {
        PilotAdminMainPageView pilotAdminView = new PilotAdminMainPageView();
        updateMainScene(pilotAdminView.getView());
    }

    public void openScenePlaneAdministration() {
        PlaneAdminMainPageView planeAdminView = new PlaneAdminMainPageView();
        updateMainScene(planeAdminView.getView());
    }

    public void openSceneAircrafttypeAdministration() {
        AircraftTypeAdminMainPageView aircraftTypeAdminView = new AircraftTypeAdminMainPageView();
        updateMainScene(aircraftTypeAdminView.getView());
    }

    public void openSceneCreateReservation() {
        ReservationCreationView view = new ReservationCreationView();
        reservationCreationPresenter = (ReservationCreationPresenter) view.getPresenter();
        reservationCreationPresenter.initItems();
        updateMainScene(view.getView());
    }

    public void openSceneUserReservations() {
        ReservationListView view = new ReservationListView();
        updateMainScene(view.getView());
    }

    public void openLoginDialog() {
        if (SessionManager.getInstance().isUserLoggedIn() == false) {
            Dialogs.create().showLogin(new UserInfo("", ""), info -> {
                SessionManager.getInstance().loginUser(info);
                logger.debug("User " + info.getUserName() + " has been logged in.");
                return null;
            });
        }
    }

    public void logoutUser() {
        SessionManager.getInstance().logoutUser();
        clearMainScene();
    }

    private void updateMainScene(Parent root) {
        clearMainScene();
        mainScene.getChildren().add(root);
    }

    private void clearMainScene() {
        mainScene.getChildren().clear();
    }
}
