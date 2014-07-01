/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.util.Collection;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.exception.ReservationErrorCode;
import com.prodyna.pac.flightplan.reservation.exception.ReservationWorkflowException;
import com.prodyna.pac.flightplan.scenario.Scenario2;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;

/**
 * Arquillian test for {@link ReservationWorkflowService} methods.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class ReservationWorkflowServiceTest {

    @Inject
    private ReservationWorkflowService workflowService;

    @Inject
    private ReservationService reservationService;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario2());
    }

    @Test
    @InSequence(10)
    public void testLoadReservation() {
        Collection<String> overdueLentReservationIds = workflowService.loadOverdueLentReservationIds();
        Assert.assertEquals(1, overdueLentReservationIds.size());
    }

    @Test
    @InSequence(20)
    public void testCancelReservation() {
        try {
            workflowService.cancelReservation("nowReserved");
        } catch (ReservationWorkflowException e) {
            Assert.fail("Caught unexpected exception.");
        }

        Reservation reservation = reservationService.loadReservationById("nowReserved");
        Assert.assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());

        // Cancel again and fail!
        try {
            workflowService.cancelReservation(reservation.getId());
            Assert.fail("Cancel action may not succeed as the reservation is already cancelled.");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }
    }

    @Test
    @InSequence(30)
    public void testReceiveReservationItemWithReservationId() {

        // Succeed! This is a valid action!
        try {
            workflowService.receiveReservationItemWithReservationId("nowReserved");
        } catch (ReservationWorkflowException e) {
            Assert.fail("Caught unexpected exception.");
        }
        Reservation reservation = reservationService.loadReservationById("nowReserved");
        Assert.assertEquals(ReservationStatus.LENT, reservation.getStatus());

        // Fail trying to receive the received item again
        try {
            workflowService.receiveReservationItemWithReservationId(reservation.getId());
            Assert.fail("Receive action may not succeed as the reservation is already lent.");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }

        // Fail trying to receive a cancelled reservation item
        try {
            workflowService.receiveReservationItemWithReservationId("nowCancelled");
            Assert.fail("Receive action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }

        // Fail trying to receive a reservation item in the past
        try {
            workflowService.receiveReservationItemWithReservationId("pastReserved");
            Assert.fail("Receive action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(ReservationErrorCode.RESERVATION_DATE_IN_PAST_OR_FUTURE));
        }

        // Fail trying to receive a reservation item in the future
        try {
            workflowService.receiveReservationItemWithReservationId("futureReserved");
            Assert.fail("Receive action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(ReservationErrorCode.RESERVATION_DATE_IN_PAST_OR_FUTURE));
        }
    }

    @Test
    @InSequence(40)
    public void testReturnReservationItemWithReservationId() {

        try {
            workflowService.returnReservationItemWithReservationId("pastLent");
        } catch (ReservationWorkflowException e) {
            Assert.fail("Caught unexpected exception.");
        }
        Reservation reservation = reservationService.loadReservationById("pastLent");
        Assert.assertEquals(ReservationStatus.RETURNED, reservation.getStatus());

        try {
            workflowService.returnReservationItemWithReservationId("nowLent");
        } catch (ReservationWorkflowException e) {
            Assert.fail("Caught unexpected exception.");
        }
        reservation = reservationService.loadReservationById("nowLent");
        Assert.assertEquals(ReservationStatus.RETURNED, reservation.getStatus());

        // Fail trying to return the returned item again
        try {
            workflowService.returnReservationItemWithReservationId(reservation.getId());
            Assert.fail("Return action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }

        // Fail trying to return a cancelled item
        try {
            workflowService.returnReservationItemWithReservationId("nowCancelled");
            Assert.fail("Return action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }

        // Fail trying to return a reserved item
        try {
            workflowService.returnReservationItemWithReservationId("nowReserved");
            Assert.fail("Return action may not succeed!");
        } catch (ReservationWorkflowException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(ReservationErrorCode.WRONG_RESERVATION_STATUS));
        }
    }
}
