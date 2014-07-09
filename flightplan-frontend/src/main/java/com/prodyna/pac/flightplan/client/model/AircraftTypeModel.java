/**
 * 
 */
package com.prodyna.pac.flightplan.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class AircraftTypeModel {

    private final StringProperty id;
    private final StringProperty description;
    private final IntegerProperty version;

    private final StringProperty nameValue;

    public AircraftTypeModel() {
        this.id = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.version = new SimpleIntegerProperty();

        this.nameValue = new SimpleStringProperty();
        this.nameValue.bind(description.concat(" (").concat(id).concat(")"));
    }

    public AircraftTypeModel(AircraftType type) {
        this();

        initFromEntity(type);
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public AircraftType getEntity() {
        AircraftType type = new AircraftType();
        type.setId(getId());
        type.setDescription(getDescription());
        type.setVersion(getVersion());
        return type;
    }

    public void initFromEntity(AircraftType type) {
        setId(type.getId());
        setDescription(type.getDescription());
        setVersion(type.getVersion());
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getVersion() {
        return version.get();
    }

    public void setVersion(int version) {
        this.version.set(version);
    }

    public IntegerProperty versionProperty() {
        return version;
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
        AircraftTypeModel other = (AircraftTypeModel) obj;
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

    @Override
    public String toString() {
        return nameValue.get();
    }
}
