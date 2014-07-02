/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.flightplan.pilot.service.PilotService;
import com.prodyna.pac.flightplan.plane.service.PlaneService;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationErrorCode;
import com.prodyna.pac.flightplan.planereservation.exception.PlaneReservationValidationException;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.scenario.Scenario3;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

@RunWith(Arquillian.class)
public class PlaneReservationServiceTest {

    @Inject
    private PlaneReservationService service;

    @Inject
    private PilotService pilotService;

    @Inject
    private PlaneService planeService;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario3());
    }

    @Test
    @InSequence(100)
    public void testLoadAllPlaneReservations() {
        List<PlaneReservation> loadedAllPlaneReservations = service.loadAllPlaneReservations();
        Assert.assertEquals(12, loadedAllPlaneReservations.size());
    }

    @Test
    @InSequence(200)
    public void testLoadPlaneReservationByPlaneAndDate() {
        long timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 4)).getTime();
        List<PlaneReservation> loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("1", timeInMillis);
        Assert.assertEquals(3, loadedPlaneReservations.size());

        timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 5)).getTime();
        loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("1", timeInMillis);
        Assert.assertEquals(3, loadedPlaneReservations.size());

        timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 5)).getTime();
        loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("3", timeInMillis);
        Assert.assertEquals(0, loadedPlaneReservations.size());

        timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 6)).getTime();
        loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("1", timeInMillis);
        Assert.assertEquals(0, loadedPlaneReservations.size());
    }

    @Test
    @InSequence(300)
    public void testLoadPlaneReservationByUserId() {
        List<PlaneReservation> listUser1 = service.loadPlaneReservationsByUserId("1");
        Assert.assertEquals(6, listUser1.size());

        List<PlaneReservation> listUser2 = service.loadPlaneReservationsByUserId("2");
        Assert.assertEquals(3, listUser2.size());

        List<PlaneReservation> listUser3 = service.loadPlaneReservationsByUserId("3");
        Assert.assertEquals(3, listUser3.size());

        List<PlaneReservation> listNotExistingUser = service.loadPlaneReservationsByUserId("not_existing_id");
        Assert.assertEquals(0, listNotExistingUser.size());
    }

    @Test
    @InSequence(400)
    public void testCreateReservationSuccess() {

        PlaneReservation res = createPlaneReservation();

        try {
            PlaneReservation createdRes = service.createReservation(res);
            Assert.assertNotNull(createdRes);
            Assert.assertEquals(res.getId(), createdRes.getId());
            Assert.assertEquals(res.getPilot(), createdRes.getPilot());
            Assert.assertEquals(res.getPlane(), createdRes.getPlane());
            Assert.assertEquals(res.getStartTime(), createdRes.getStartTime());
            Assert.assertEquals(res.getEndTime(), createdRes.getEndTime());
            Assert.assertEquals(res.getStatus(), createdRes.getStatus());
            Assert.assertEquals(res.getVersion(), createdRes.getVersion());
        } catch (Exception e) {
            Assert.fail("Unexpected exception" + e);
        }
    }

    @Test
    @InSequence(401)
    public void testCreateReservationError() {

        PlaneReservation res = createPlaneReservation();
        res.setId(null);
        res.setPilot(null);
        res.setPlane(null);
        res.setStartTime(null);
        res.setEndTime(null);
        res.setStatus(null);

        try {
            service.createReservation(res);
            Assert.fail("exception expected ");
        } catch (PlaneReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneReservationErrorCode.PILOT_MAY_NOT_BE_NULL));
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneReservationErrorCode.PLANE_MAY_NOT_BE_NULL));
        } catch (Exception e) {
            Assert.fail("PlaneReservationValidationException expected.");
        }
    }

    @Test
    @InSequence(500)
    public void testDeleteReservationByIdSuccess() {

        try {
            service.deleteReservationById("12");
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }

        List<PlaneReservation> allResList = service.loadAllPlaneReservations();
        Assert.assertEquals(11, allResList.size());
    }

    @Test
    @InSequence(501)
    public void testDeleteReservationByIdError() {

        try {
            service.deleteReservationById(null);
            Assert.fail("Exception expected");
        } catch (PlaneReservationValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneReservationErrorCode.ID_MAY_NOT_BE_NULL));
        } catch (Exception e) {
            Assert.fail("Unexpected exception");
        }

        try {
            service.deleteReservationById("");
            Assert.fail("Exception expected");
        } catch (PlaneReservationValidationException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(PlaneReservationErrorCode.RESERVATION_CANNOT_BE_DELETED));
        } catch (Exception e) {
            Assert.fail("Unexpected exception" + e);
        }

        try {
            service.deleteReservationById("not_existing_id");
            Assert.fail("Exception expected");
        } catch (PlaneReservationValidationException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(PlaneReservationErrorCode.RESERVATION_CANNOT_BE_DELETED));
        } catch (Exception e) {
            Assert.fail("Unexpected exception");
        }
    }

    @Test
    @InSequence(600)
    public void testUpdateReservation() {

        // Push reservation for 30min
        Date newStartDate = generateDate(2014, 7, 6, 20, 30, 00);
        Date newEndDate = generateDate(2014, 7, 6, 21, 30, 00);

        PlaneReservation res = createPlaneReservation();
        res.setStartTime(newStartDate);
        res.setEndTime(newEndDate);

        try {
            PlaneReservation updatedRes = service.updateReservation(res);
            Assert.assertEquals(newStartDate, updatedRes.getStartTime());
            Assert.assertEquals(newEndDate, updatedRes.getEndTime());

        } catch (Exception e) {
            Assert.fail("Unexpected exception");
        }
    }

    private PlaneReservation createPlaneReservation() {
        PlaneReservation res = new PlaneReservation();
        res.setId("20");
        res.setStartTime(generateDate(2014, 7, 6, 20, 00, 00));
        res.setEndTime(generateDate(2014, 7, 6, 21, 00, 00));
        res.setStatus(ReservationStatus.RESERVED);
        try {
            res.setPilot(pilotService.loadPilotById("1"));
        } catch (PilotNotFoundException e) {
            e.printStackTrace();
        }
        res.setPlane(planeService.loadPlaneById("1"));
        return res;
    }

    private Date generateDate(int year, int month, int day, int hour, int min, int sec) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime startTime = LocalTime.of(hour, min, sec);
        LocalDateTime localStartDate = LocalDateTime.of(date, startTime);
        return LocalDateConverter.localDateTimeToDate(localStartDate);
    }

}
