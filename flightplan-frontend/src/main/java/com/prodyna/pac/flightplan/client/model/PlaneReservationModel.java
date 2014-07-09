/**
 * 
 */
package com.prodyna.pac.flightplan.client.model;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneReservationModel {

    private final StringProperty id;
    private final IntegerProperty version;
    private final ObjectProperty<PilotModel> pilot;
    private final ObjectProperty<PlaneModel> plane;
    private final ObjectProperty<LocalDateTime> startTime;
    private final ObjectProperty<LocalDateTime> endTime;
    private final ObjectProperty<ReservationStatus> state;

    private final StringProperty pilotName;
    private final StringProperty planeName;
    private final StringProperty planeNumberPlate;

    public PlaneReservationModel() {
        id = new SimpleStringProperty();
        version = new SimpleIntegerProperty();
        pilot = new SimpleObjectProperty<>();
        plane = new SimpleObjectProperty<>();
        startTime = new SimpleObjectProperty<>();
        endTime = new SimpleObjectProperty<>();
        state = new SimpleObjectProperty<>();

        pilotName = new SimpleStringProperty("");
        planeName = new SimpleStringProperty("");
        planeNumberPlate = new SimpleStringProperty("");
    }

    /**
     * @param res
     */
    public PlaneReservationModel(PlaneReservation reservation) {
        this();

        initFromEntity(reservation);

        if (pilot.get() != null) {
            pilotName.bind(pilot.get().firstNameProperty().concat(" ").concat(pilot.get().lastNameProperty()));
        }
        if (plane.get() != null) {
            planeNumberPlate.bind(plane.get().numberPlateProperty());
            planeName.bind(plane.get().nameProperty());
        }
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param reservation
     */
    private void initFromEntity(PlaneReservation reservation) {
        id.set(reservation.getId());
        version.set(reservation.getVersion());
        pilot.set(new PilotModel(reservation.getPilot()));
        plane.set(new PlaneModel(reservation.getPlane()));
        startTime.set(LocalDateConverter.dateToLocalDateTime(reservation.getStartTime()));
        endTime.set(LocalDateConverter.dateToLocalDateTime(reservation.getEndTime()));
        state.set(reservation.getStatus());
    }

    public StringProperty idProperty() {
        return id;
    };

    public IntegerProperty versionProperty() {
        return version;
    }

    public ObjectProperty<PilotModel> pilotProperty() {
        return pilot;
    };

    public ObjectProperty<PlaneModel> planeProperty() {
        return plane;
    };

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    };

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    };

    public ObjectProperty<ReservationStatus> stateProperty() {
        return state;
    };

    public StringProperty pilotNameProperty() {
        return pilotName;
    }

    public StringProperty planeNameProperty() {
        return planeName;
    }

    public StringProperty planeNumberPlateProperty() {
        return planeNumberPlate;
    }
}
