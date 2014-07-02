/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationItem;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationValidationException;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;
import com.prodyna.pac.flightplan.user.entity.User;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * Arquillian test for {@link ReservationService} methods.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class ReservationServiceTest {

    @Inject
    private ReservationService reservationService;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Test
    @InSequence(10)
    public void testLoadReservation() {
        Reservation existingReservation = reservationService.loadReservationById("1");
        Assert.assertNotNull(existingReservation);

        Reservation notExistingReservation = reservationService.loadReservationById("7");
        Assert.assertNull(notExistingReservation);
    }

    @Test
    @InSequence(30)
    public void testCreateReservationWithIdNull() {

        Reservation reservation = generateTestReservation();
        reservation.setId(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null id may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.ID_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("Unexpected exception " + ex);
        }
    }

    @Test
    @InSequence(40)
    public void testCreateReservationWithStarttimeNull() {

        Reservation reservation = generateTestReservation();
        reservation.setStart(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null startTime may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.STARTTIME_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(50)
    public void testCreateReservationWithEndtimeNull() {

        Reservation reservation = generateTestReservation();
        reservation.setEnd(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null endTime may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.ENDTIME_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(60)
    public void testCreateReservationWithStartAfterEndtime() {

        Date endDate = generateDate(2014, 7, 4, 14, 0, 0);
        Date startDate = generateDate(2014, 7, 4, 14, 0, 1);

        Reservation reservation = generateTestReservation();
        reservation.setStart(startDate);
        reservation.setEnd(endDate);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with endTime before startTime may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.STARTTIME_MUST_BE_BEFORE_ENDTIME));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(70)
    public void testCreateReservationWithStatusNull() {

        Reservation reservation = generateTestReservation();
        reservation.setStatus(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null status may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.STATUS_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(80)
    public void testCreateReservationWithUserNull() {

        Reservation reservation = generateTestReservation();
        reservation.setUser(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null user may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.USER_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(90)
    public void testCreateReservationWithItemNull() {

        Reservation reservation = generateTestReservation();
        reservation.setItem(null);

        try {
            reservationService.createReservation(reservation);

            Assert.fail("Reservation with null item may not be created!");
        } catch (ReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes()
                    .contains(ReservationErrorCode.RESERVATION_ITEM_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No ReservationValidationException has been thrown.");
        }
    }

    @Test
    @InSequence(100)
    public void testCreateReservationWithExistingId() {

        Reservation reservation = generateTestReservation();
        reservation.setId("1");

        try {
            reservationService.createReservation(reservation);
            Assert.fail("Reservation with id 1 may not be created as it already exists.");
        } catch (Exception ex) {
        }
    }

    @Test
    @InSequence(105)
    public void testCreateReservationSuccess() {

        Reservation reservation = generateTestReservation();
        Reservation createdReservation;
        try {
            createdReservation = reservationService.createReservation(reservation);
        } catch (ReservationValidationException e) {
            Assert.fail("Caught an exception that was not expected.");
            createdReservation = null;
        }

        Assert.assertNotNull(createdReservation);
        Assert.assertEquals(reservation, createdReservation);
    }

    @Test
    @InSequence(110)
    public void testDeleteReservationById() {

        try {
            reservationService.deleteReservationById("1");
        } catch (Exception e) {
            Assert.fail("unexpected exception");
        }
        Reservation loadDeletedReservation = reservationService.loadReservationById("1");
        Assert.assertNull(loadDeletedReservation);
    }

    @Test
    @InSequence(120)
    public void testUpdateReservation() {

        Reservation res = reservationService.loadReservationById("1");
        Date endDate = res.getEnd();
        LocalDateTime localEndDate = LocalDateConverter.dateToLocalDateTime(endDate);
        localEndDate = localEndDate.plusHours(1);
        endDate = LocalDateConverter.localDateTimeToDate(localEndDate);
        res.setEnd(endDate);

        try {
            reservationService.updateReservation(res);
        } catch (ReservationValidationException e) {
            Assert.fail("Error updating reservation.");
        }
    }

    @Test
    @InSequence(130)
    public void testFindConflictingReservationIds() {
        Reservation reservation = generateTestReservation();

        reservation.getItem().setId("2");
        reservation.setStart(generateDate(2014, 7, 4, 13, 00, 00));
        reservation.setEnd(generateDate(2014, 7, 4, 18, 00, 00));

        Collection<String> conflictingReservationIds = reservationService.findConflictingReservationIds(reservation);
        Assert.assertEquals(2, conflictingReservationIds.size());
        Assert.assertEquals(true, conflictingReservationIds.contains("4"));
        Assert.assertEquals(true, conflictingReservationIds.contains("5"));

        reservation.getItem().setId("1");
        conflictingReservationIds = reservationService.findConflictingReservationIds(reservation);
        Assert.assertEquals(1, conflictingReservationIds.size());
        Assert.assertEquals(true, conflictingReservationIds.contains("3"));

        reservation.setStart(generateDate(2014, 7, 4, 14, 00, 00));
        conflictingReservationIds = reservationService.findConflictingReservationIds(reservation);
        Assert.assertEquals(0, conflictingReservationIds.size());

        reservation.setStart(generateDate(2014, 7, 4, 13, 59, 59));
        conflictingReservationIds = reservationService.findConflictingReservationIds(reservation);
        Assert.assertEquals(1, conflictingReservationIds.size());
        Assert.assertEquals(true, conflictingReservationIds.contains("3"));

        reservation.getUser().setId("2");
        conflictingReservationIds = reservationService.findConflictingReservationIds(reservation);
        Assert.assertEquals(1, conflictingReservationIds.size());
        Assert.assertEquals(true, conflictingReservationIds.contains("3"));
    }

    private Date generateDate(int year, int month, int day, int hour, int min, int sec) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime startTime = LocalTime.of(hour, min, sec);
        LocalDateTime localStartDate = LocalDateTime.of(date, startTime);
        return LocalDateConverter.localDateTimeToDate(localStartDate);
    }

    private Reservation generateTestReservation() {

        ReservationItem item = generateReservationItem();
        User user = generateUser();

        Date startDate = generateDate(2014, 7, 4, 14, 0, 0);
        Date endDate = generateDate(2014, 7, 4, 15, 0, 0);

        Reservation reservation = new Reservation();
        reservation.setId("10");
        reservation.setVersion(1);
        reservation.setStart(startDate);
        reservation.setEnd(endDate);
        reservation.setStatus(ReservationStatus.RESERVED);
        reservation.setUser(user);
        reservation.setItem(item);

        return reservation;
    }

    private User generateUser() {
        User user = new User();
        user.setId("1");
        user.setVersion(1);
        return user;
    }

    private ReservationItem generateReservationItem() {
        ReservationItem item = new ReservationItem();
        item.setId("1");
        item.setVersion(1);
        return item;
    }
}
