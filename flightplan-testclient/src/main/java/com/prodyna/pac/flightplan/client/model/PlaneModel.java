/**
 * 
 */
package com.prodyna.pac.flightplan.client.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.plane.entity.Plane;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneModel {

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty numberPlate;
    private final ObjectProperty<AircraftTypeModel> aircraftType;

    private final StringProperty nameAsEntryList;

    public PlaneModel() {
        id = new SimpleStringProperty();
        name = new SimpleStringProperty();
        numberPlate = new SimpleStringProperty();
        aircraftType = new SimpleObjectProperty<AircraftTypeModel>();

        nameAsEntryList = new SimpleStringProperty();
        nameAsEntryList.bind(name.concat(" ").concat(numberPlate).concat(" (").concat(id).concat(")"));
    }

    public PlaneModel(Plane plane) {
        this();

        initFromEntity(plane);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public Plane getEntity() {
        Plane plane = new Plane();
        plane.setId(getId());
        plane.setName(getName());
        plane.setNumberPlate(getNumberPlate());
        plane.setAircraftType(getAircraftType().getEntity());
        return plane;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param plane
     */
    public void initFromEntity(Plane plane) {
        setId(plane.getId());
        setName(plane.getName());
        setNumberPlate(plane.getNumberPlate());
        setAircraftType(new AircraftTypeModel(plane.getAircraftType()));
    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getNumberPlate() {
        return numberPlate.get();
    }

    public AircraftTypeModel getAircraftType() {
        return aircraftType.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate.set(numberPlate);
    }

    public void setAircraftType(AircraftTypeModel aircraftType) {
        this.aircraftType.set(aircraftType);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty numberPlateProperty() {
        return numberPlate;
    }

    public ObjectProperty<AircraftTypeModel> aircraftTypeProperty() {
        return aircraftType;
    }

    @Override
    public String toString() {
        return nameAsEntryList.get();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null || id.get() == null) ? 0 : id.get().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlaneModel other = (PlaneModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (other.id == null) {
            return false;
        } else if (id.get() == null) {
            if (other.id.get() != null) {
                return false;
            }
        } else if (other.id.get() == null) {
            return false;
        } else if (!id.get().equals(other.id.get()))
            return false;
        return true;
    }
}
