/**
 * 
 */
package com.prodyna.pac.flightplan.client.aircrafttypeadmin.details;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.service.AircraftTypeClientService;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeDetailsViewModel {

    private final ObjectProperty<AircraftTypeModel> selectedType;
    private final ObjectProperty<AircraftTypeModel> currentType;

    private final AircraftTypeClientService service;

    public AircraftTypeDetailsViewModel() {

        this.selectedType = new SimpleObjectProperty<>();
        this.currentType = new SimpleObjectProperty<>();

        this.service = new AircraftTypeClientService();
    }

    public void saveAircraftType() {
        AircraftType type = currentType.get().getEntity();
        AircraftType returnValue;
        if (type.getId() == null) {
            returnValue = service.createAircraftType(type);
        } else {
            returnValue = service.updateAircraftType(type);
        }
        currentType.set(new AircraftTypeModel(returnValue));
    }

    public void loadAircraftTypeById(String aircraftTypeId) {
        AircraftType type = service.loadAircraftTypeById(aircraftTypeId);
        currentType.set(new AircraftTypeModel(type));
    }

    public void newAircraftType() {
        currentType.set(new AircraftTypeModel());
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObjectProperty<AircraftTypeModel> selectedTypeProperty() {
        return selectedType;
    }

    public ObjectProperty<AircraftTypeModel> currentTypeProperty() {
        return currentType;
    };
}
