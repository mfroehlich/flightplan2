/**
 * 
 */
package com.prodyna.pac.flightplan.plane.service;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.common.exception.TechnicalException;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.plane.exception.PlaneErrorCode;
import com.prodyna.pac.flightplan.plane.exception.PlaneValidationException;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;

/**
 * Arquillian test for {@link PlaneService} methods.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class PlaneServiceTest {

    @Inject
    private PlaneService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Test
    @InSequence(10)
    public void testCreatePlaneSuccess() {

        Plane plane = createPlane();
        Plane createdPlane;
        try {
            createdPlane = service.createPlane(plane);
            Assert.assertEquals(plane.getId(), createdPlane.getId());
            Assert.assertEquals(plane.getName(), createdPlane.getName());
            Assert.assertEquals(plane.getNumberPlate(), createdPlane.getNumberPlate());
            Assert.assertEquals(plane.getAircraftType(), createdPlane.getAircraftType());
            Assert.assertEquals(1, createdPlane.getVersion());
        } catch (PlaneValidationException e) {
            Assert.fail("Caught unexpected exception.");
        }
    }

    @Test
    @InSequence(11)
    public void testCreatePlaneIdNull() {
        Plane plane = createPlane();
        plane.setId(null);

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.ID_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            // TODO mfroehlich Hier wird eine javax.ejb.EJBException geworfen! Warum auch immer!
            if (ex.getCause() instanceof TechnicalException) {
                Assert.assertEquals(true, true);
            } else {
                ex.printStackTrace();
                Assert.fail("No TechnicalException has been thrown: " + ex.getMessage());
            }
        }
    }

    @Test
    @InSequence(12)
    public void testCreatePlaneNameInvalid() {
        Plane plane = createPlane();
        plane.setName(null);

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }

        // Too long
        plane.setName("12345678901234567890123456789012345678901234567890A");

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }

        // empty
        plane.setName("");

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NAME_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }
    }

    @Test
    @InSequence(13)
    public void testCreatePlaneNumberPlateInvalid() {
        Plane plane = createPlane();
        plane.setNumberPlate(null);

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NUMBERPLATE_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }

        // Too long
        plane.setNumberPlate("12345678901234567890123456789012345678901234567890A");

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NUMBERPLATE_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }

        // empty
        plane.setNumberPlate("");

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NUMBERPLATE_INVALID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }
    }

    @Test
    @InSequence(14)
    public void testCreatePlaneAircraftTypeNull() {
        Plane plane = createPlane();
        plane.setAircraftType(null);

        try {
            service.createPlane(plane);
            Assert.fail("Creation should have failed!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.AIRCRAFTTYPE_MUST_NOT_BE_NULL));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }
    }

    @Test
    @InSequence(20)
    public void testDeletePlaneByIdFail() {

        Plane plane = service.loadPlaneById("1");
        Assert.assertNotNull(plane);
        try {
            service.deletePlaneById("1");
            Assert.fail("Plane is referenced by reservation and cannot be deleted.");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.PLANE_CANNOT_BE_DELETED));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown:" + ex.getMessage());
        }
    }

    @Test
    @InSequence(21)
    public void testDeletePlaneByIdSuccess() {
        try {
            service.deletePlaneById("3");
        } catch (Exception e1) {
            Assert.fail("Deletion must work.");
        }
        Plane plane = service.loadPlaneById("3");
        Assert.assertNull(plane);

        List<Plane> allPlanes = service.loadAllPlanes();
        Assert.assertEquals(2, allPlanes.size());
    }

    @Test
    @InSequence(22)
    public void testDeletePlaneByNotExistingId() {
        try {
            // not existing id
            service.deletePlaneById("4");
            Assert.fail("Deleting plane with not existing ID must result in Exception!");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.PLANE_NOT_FOUND_BY_ID));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }
    }

    @Test
    @InSequence(30)
    public void testLoadAllPlanes() {
        List<Plane> loadAllPlanes = service.loadAllPlanes();
        Assert.assertEquals(3, loadAllPlanes.size());
    }

    @Test
    @InSequence(40)
    public void testLoadPlaneById() {
        Plane plane = service.loadPlaneById("1");
        Assert.assertNotNull(plane);

        Assert.assertEquals("1", plane.getId());
        Assert.assertEquals("Plane 1", plane.getName());
        Assert.assertEquals("PLA-NE-1", plane.getNumberPlate());
        Assert.assertEquals("1", plane.getAircraftType().getId());

        plane = service.loadPlaneById("4");
        Assert.assertNull(plane);
    }

    @Test
    @InSequence(50)
    public void testUpdatePlane() {

        Plane plane = service.loadPlaneById("1");
        plane.setName("New name");

        try {
            Plane updatedPlane = service.updatePlane(plane);
            Assert.assertEquals("New name", updatedPlane.getName());
            Assert.assertEquals("1", updatedPlane.getId());
            Assert.assertEquals(2, updatedPlane.getVersion());

        } catch (PlaneValidationException e) {
            e.printStackTrace();
        }

        plane = service.loadPlaneById("2");
        plane.setName("New name");

        try {
            service.updatePlane(plane);
            Assert.fail("Invalid updated should have lead to exception.");
        } catch (PlaneValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(PlaneErrorCode.NAME_AND_NUMBERPLATE_MUST_BE_UNIQUE));
        } catch (Exception ex) {
            Assert.fail("No PlaneValidationException thrown");
        }
    }

    private Plane createPlane() {
        Plane plane = new Plane();
        plane.setId("4");
        plane.setAircraftType(createAircraftType());
        plane.setName("Plane 4");
        plane.setNumberPlate("PL-ANE-4");
        plane.setVersion(1);
        return plane;
    }

    private AircraftType createAircraftType() {
        AircraftType type = new AircraftType();
        type.setId("1");
        type.setDescription("Type 1");
        type.setVersion(1);
        return type;
    }
}
