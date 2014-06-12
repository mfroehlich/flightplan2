/**
 * 
 */
package com.prodyna.pac.flightplan.client.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotModel {

    private final StringProperty id;
    private final StringProperty userName;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty password;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> licenceValidity;

    // TODO mfroehlich List of assigned aircrafttypes missing

    private final StringProperty nameAsEntryList;

    public PilotModel() {
        id = new SimpleStringProperty();
        userName = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        password = new SimpleStringProperty();
        email = new SimpleStringProperty();
        licenceValidity = new SimpleObjectProperty<LocalDate>();

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
        userName.set(pilot.getUserName());
        firstName.set(pilot.getFirstName());
        lastName.set(pilot.getLastName());
        password.set(pilot.getPassword());
        email.set(pilot.geteMailAddress());
        licenceValidity.set(LocalDateConverter.dateToLocalDate(pilot.getLicenceValidity()));
    }

    public Pilot getEntity() {
        Pilot pilot = new Pilot();
        pilot.setId(id.get());
        pilot.setUserName(userName.get());
        pilot.setFirstName(firstName.get());
        pilot.setLastName(lastName.get());
        pilot.setPassword(password.get());
        pilot.seteMailAddress(email.get());
        pilot.setLicenceValidity(LocalDateConverter.localDateToDate(licenceValidity.get()));
        return pilot;
    }

    @Override
    public String toString() {
        return nameAsEntryList.get();
    }

    public StringProperty idProperty() {
        return id;
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

}
