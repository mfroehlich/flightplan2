/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.details;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.model.PilotModel;
import com.prodyna.pac.flightplan.client.service.AircraftTypeClientService;
import com.prodyna.pac.flightplan.client.service.PilotClientService;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.exception.PilotValidationException;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotDetailsViewModel {

    private final ObjectProperty<PilotModel> selectedPilot;
    private final ObjectProperty<PilotModel> currentPilot;

    private final PilotClientService service;
    private final AircraftTypeClientService typeService;

    public PilotDetailsViewModel() {

        this.selectedPilot = new SimpleObjectProperty<>();
        this.currentPilot = new SimpleObjectProperty<>();

        this.service = new PilotClientService();
        this.typeService = new AircraftTypeClientService();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @throws PilotValidationException
     * @throws UserValidationException
     */
    public void savePilot() throws PilotValidationException, UserValidationException {
        Pilot pilot = currentPilot.get().getEntity();
        Pilot returnValue;
        if (pilot.getId() == null) {
            returnValue = service.createPilot(pilot);
        } else {
            returnValue = service.updatePilot(pilot);
        }
        currentPilot.set(new PilotModel(returnValue));
    }

    public void loadPilotById(String pilotId) throws PilotNotFoundException {
        Pilot pilot = service.loadPilotById(pilotId);
        this.currentPilot.set(new PilotModel(pilot));
    }

    public ObjectProperty<PilotModel> selectedPilotProperty() {
        return this.selectedPilot;
    }

    public ObjectProperty<PilotModel> currentPilotProperty() {
        return this.currentPilot;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<AircraftTypeModel> loadAllAircraftTypes() {
        ObservableList<AircraftTypeModel> types = FXCollections.observableArrayList();
        List<AircraftType> allAircraftTypes = typeService.loadAllAircraftTypes();
        allAircraftTypes.forEach(type -> types.add(new AircraftTypeModel(type)));
        return types;
    }
}
