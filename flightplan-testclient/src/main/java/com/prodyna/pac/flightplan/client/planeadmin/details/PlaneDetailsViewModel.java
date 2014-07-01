/**
 * 
 */
package com.prodyna.pac.flightplan.client.planeadmin.details;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.service.AircraftTypeClientService;
import com.prodyna.pac.flightplan.client.service.PlaneClientService;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneDetailsViewModel {

    private final ObjectProperty<PlaneModel> selectedPlane;
    private final ObjectProperty<PlaneModel> currentPlane;

    private final PlaneClientService service;
    private final AircraftTypeClientService aircraftTypeservice;

    public PlaneDetailsViewModel() {
        this.selectedPlane = new SimpleObjectProperty<>();
        this.currentPlane = new SimpleObjectProperty<>();

        this.service = new PlaneClientService();
        this.aircraftTypeservice = new AircraftTypeClientService();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @throws PlaneValidationException
     */
    public void savePlane() throws PlaneValidationException {
        Plane plane = this.currentPlane.get().getEntity();
        Plane returnValue;
        if (plane.getId() == null) {
            returnValue = service.createPlane(plane);
        } else {
            returnValue = service.updatePlane(plane);
        }
        this.currentPlane.set(new PlaneModel(returnValue));
    }

    public void loadPlaneById(String planeId) {
        Plane plane = service.loadPlaneById(planeId);
        this.currentPlane.set(new PlaneModel(plane));
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<AircraftTypeModel> loadAircraftTypes() {
        List<AircraftType> aircraftTypes = aircraftTypeservice.loadAllAircraftTypes();
        ObservableList<AircraftTypeModel> typeList = FXCollections.observableArrayList();
        aircraftTypes.forEach(a -> typeList.add(new AircraftTypeModel(a)));
        return typeList;
    }

    public ObjectProperty<PlaneModel> selectedPlaneProperty() {
        return this.selectedPlane;
    }

    public ObjectProperty<PlaneModel> currentPlaneProperty() {
        return this.currentPlane;
    }
}
