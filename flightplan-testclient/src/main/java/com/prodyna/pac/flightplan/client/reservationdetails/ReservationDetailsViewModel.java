/**
 * 
 */
package com.prodyna.pac.flightplan.client.reservationdetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.model.PlaneReservationModel;
import com.prodyna.pac.flightplan.client.service.PlaneClientService;
import com.prodyna.pac.flightplan.client.service.ReservationClientService;
import com.prodyna.pac.flightplan.client.session.SessionManager;
import com.prodyna.pac.flightplan.common.entity.User;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ReservationDetailsViewModel {

    ReservationClientService reservationClientService;
    PlaneClientService planeClientService;

    private final StringProperty id;
    private final StringProperty pilotName;
    private final ObjectProperty<PlaneModel> selectedPlane;
    private final ObjectProperty<LocalDate> selectedDate;
    private final StringProperty startHour;
    private final StringProperty startMinute;
    private final StringProperty endHour;
    private final StringProperty endMinute;

    public ReservationDetailsViewModel() {
        reservationClientService = new ReservationClientService();
        planeClientService = new PlaneClientService();

        id = new SimpleStringProperty();
        pilotName = new SimpleStringProperty();
        selectedPlane = new SimpleObjectProperty<>();
        selectedDate = new SimpleObjectProperty<>();
        startHour = new SimpleStringProperty();
        startMinute = new SimpleStringProperty();
        endHour = new SimpleStringProperty();
        endMinute = new SimpleStringProperty();
    }

    public void initModel(PlaneReservationModel model) {
        ObjectProperty<LocalDateTime> startTime = model.startTimeProperty();
        ObjectProperty<LocalDateTime> endTime = model.endTimeProperty();
        PlaneModel planeModel = model.planeProperty().get();

        User loggedInUser = SessionManager.getInstance().getLoggedInUser();
        String pilotNameText = loggedInUser.getFirstName() + " " + loggedInUser.getLastName();

        id.set(model.idProperty().get());
        pilotName.set(pilotNameText);
        selectedPlane.set(planeModel);
        selectedDate.set(startTime.get().toLocalDate());
        startHour.set(getTwoDigitNumber(startTime.get().getHour()));
        startMinute.set(getTwoDigitNumber(startTime.get().getMinute()));
        endHour.set(getTwoDigitNumber(endTime.get().getHour()));
        endMinute.set(getTwoDigitNumber(endTime.get().getMinute()));
    }

    public void initModel(PlaneModel planeModel, LocalDate selectedLocalDate) {
        User loggedInUser = SessionManager.getInstance().getLoggedInUser();
        String pilotNameText = loggedInUser.getFirstName() + " " + loggedInUser.getLastName();

        LocalDateTime now = LocalDateTime.now();
        int currentHourOfDay = now.getHour();

        id.set(null);
        pilotName.set(pilotNameText);
        selectedPlane.set(planeModel);
        selectedDate.set(selectedLocalDate);
        startHour.set(getTwoDigitNumber(currentHourOfDay));
        startMinute.set("00");
        endHour.set(getTwoDigitNumber(currentHourOfDay + 1));
        endMinute.set("00");
    }

    /**
     * Create or update the reservation represented by the properties of this object.
     */
    public void createReservation() {

        // TODO mfroehlich Null checks required!
        int startHourInt = new Integer(startHour.get());
        int endHourInt = new Integer(endHour.get());
        int startMinuteInt = new Integer(startMinute.get());
        int endMinuteInt = new Integer(endMinute.get());

        LocalDate localDate = selectedDate.get();
        LocalTime localStartTime = LocalTime.of(startHourInt, startMinuteInt);
        LocalTime localEndTime = LocalTime.of(endHourInt, endMinuteInt);

        Date startTime = LocalDateConverter.localDateTimeToDate(LocalDateTime.of(localDate, localStartTime));
        Date endTime = LocalDateConverter.localDateTimeToDate(LocalDateTime.of(localDate, localEndTime));

        // TODO mfroehlich Null checks required!
        Plane plane = selectedPlane.get().getEntity();

        User user = SessionManager.getInstance().getLoggedInUser();

        String reservationId = id.get();
        boolean updateExistingReservation = reservationId != null;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setItem(plane);
        reservation.setUser(user);
        reservation.setStart(startTime);
        reservation.setEnd(endTime);
        reservation.setStatus(ReservationStatus.RESERVED);

        if (updateExistingReservation == true) {
            reservationClientService.updateReservation(reservation);
        } else {
            reservation.setId(UUID.randomUUID().toString());
            reservationClientService.createReservation(reservation);
        }
    }

    public ObservableList<PlaneModel> loadAllPlanes() {
        List<Plane> loadAllPlanes = planeClientService.loadAllPlanes();
        ObservableList<PlaneModel> planeList = FXCollections.observableArrayList();
        loadAllPlanes.forEach(plane -> planeList.add(new PlaneModel(plane)));
        return planeList;
    }

    public StringProperty pilotNameProperty() {
        return pilotName;
    }

    public ObjectProperty<PlaneModel> selectedPlaneProperty() {
        return selectedPlane;
    }

    public ObjectProperty<LocalDate> selectedDateProperty() {
        return selectedDate;
    }

    public StringProperty startHourProperty() {
        return startHour;
    }

    public StringProperty startMinuteProperty() {
        return startMinute;
    }

    public StringProperty endHourProperty() {
        return endHour;
    }

    public StringProperty endMinuteProperty() {
        return endMinute;
    }

    private String getTwoDigitNumber(int number) {
        String numberStr = String.valueOf(number);
        if (numberStr.length() == 1) {
            numberStr = "0" + numberStr;
        }
        return numberStr;
    }
}
