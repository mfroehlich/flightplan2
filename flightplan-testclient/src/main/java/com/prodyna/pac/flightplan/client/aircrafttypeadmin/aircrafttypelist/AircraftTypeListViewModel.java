/**
 * 
 */
package com.prodyna.pac.flightplan.client.aircrafttypeadmin.aircrafttypelist;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.AircraftTypeModel;
import com.prodyna.pac.flightplan.client.service.AircraftTypeClientService;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeListViewModel {

    private final ObjectProperty<AircraftTypeModel> selectedAircraftType;

    private final AircraftTypeClientService service;

    public AircraftTypeListViewModel() {
        this.selectedAircraftType = new SimpleObjectProperty<>();
        this.service = new AircraftTypeClientService();
    }

    public void deleteAircraftType() throws AircraftTypeValidationException {
        AircraftTypeModel selectedType = selectedAircraftType.get();
        if (selectedType != null) {
            String aircraftTypeId = selectedType.getId();
            service.deleteAircraftTypeById(aircraftTypeId);
        }
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<AircraftTypeModel> loadAllAircraftTypes() {
        ObservableList<AircraftTypeModel> typeList = FXCollections.observableArrayList();
        List<AircraftType> allTypes = service.loadAllAircraftTypes();
        allTypes.forEach(type -> typeList.add(new AircraftTypeModel(type)));
        return typeList;
    }

    public ObjectProperty<AircraftTypeModel> selectedAircraftTypeProperty() {
        return selectedAircraftType;
    }
}
