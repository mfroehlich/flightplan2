/**
 * 
 */
package com.prodyna.pac.flightplan.client.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotModel {

    private final StringProperty id;
    private final IntegerProperty version;
    private final StringProperty userName;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty password;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> licenceValidity;
    private final ObjectProperty<List<AircraftTypeModel>> assignedAircraftTypes;

    private final StringProperty nameAsEntryList;

    public PilotModel() {
        id = new SimpleStringProperty();
        version = new SimpleIntegerProperty();
        userName = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        password = new SimpleStringProperty();
        email = new SimpleStringProperty();
        licenceValidity = new SimpleObjectProperty<>();
        assignedAircraftTypes = new SimpleObjectProperty<>();

        nameAsEntryList = new SimpleStringProperty();
        nameAsEntryList.bind(firstName.concat(" ").concat(lastName).concat(" (").concat(userName).concat(" / ")
                .concat(id).concat(")"));
    }

    public PilotModel(Pilot pilot) {
        this();
        initFromEntity(pilot);
    }

    public void initFromEntity(Pilot pilot) {
        id.set(pilot.getId());
        version.set(pilot.getVersion());
        userName.set(pilot.getUserName());
        firstName.set(pilot.getFirstName());
        lastName.set(pilot.getLastName());
        password.set(pilot.getPassword());
        email.set(pilot.geteMailAddress());
        licenceValidity.set(LocalDateConverter.dateToLocalDate(pilot.getLicenceValidity()));

        List<AircraftType> assignedAircraftTypes = pilot.getAssignedAircraftTypes();
        List<AircraftTypeModel> aircraftTypes = new ArrayList<>();
        if (assignedAircraftTypes != null) {
            assignedAircraftTypes.forEach(type -> aircraftTypes.add(new AircraftTypeModel(type)));
        }
        this.assignedAircraftTypes.set(aircraftTypes);
    }

    public Pilot getEntity() {
        Pilot pilot = new Pilot();
        pilot.setId(id.get());
        pilot.setVersion(version.get());
        pilot.setUserName(userName.get());
        pilot.setFirstName(firstName.get());
        pilot.setLastName(lastName.get());
        pilot.setPassword(password.get());
        pilot.seteMailAddress(email.get());
        pilot.setLicenceValidity(LocalDateConverter.localDateToDate(licenceValidity.get()));

        List<AircraftType> assignedTypes = new ArrayList<>();
        assignedAircraftTypes.get().forEach(type -> assignedTypes.add(type.getEntity()));
        pilot.setAssignedAircraftTypes(assignedTypes);

        return pilot;
    }

    @Override
    public String toString() {
        return nameAsEntryList.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public IntegerProperty versionProperty() {
        return version;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public ObjectProperty<LocalDate> licenceValidityProperty() {
        return licenceValidity;
    }

    public ObjectProperty<List<AircraftTypeModel>> assignedAircraftTypesProperty() {
        return assignedAircraftTypes;
    }

}
