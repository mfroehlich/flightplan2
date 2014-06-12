/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.details;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.prodyna.pac.flightplan.client.model.PilotModel;
import com.prodyna.pac.flightplan.client.service.PilotClientService;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;

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

    public PilotDetailsViewModel() {

        this.selectedPilot = new SimpleObjectProperty<>();
        this.currentPilot = new SimpleObjectProperty<>();

        this.service = new PilotClientService();
    }

    /**
     * TODO mfroehlich Comment me
     */
    public void savePilot() {
        Pilot pilot = currentPilot.get().getEntity();
        Pilot returnValue;
        if (pilot.getId() == null) {
            returnValue = service.createPilot(pilot);
        } else {
            returnValue = service.updatePilot(pilot);
        }
        currentPilot.set(new PilotModel(returnValue));
    }

    public void loadPilotById(String pilotId) {
        Pilot pilot = service.loadPilotById(pilotId);
        this.currentPilot.set(new PilotModel(pilot));
    }

    public ObjectProperty<PilotModel> selectedPilotProperty() {
        return this.selectedPilot;
    }

    public ObjectProperty<PilotModel> currentPilotProperty() {
        return this.currentPilot;
    }
}
