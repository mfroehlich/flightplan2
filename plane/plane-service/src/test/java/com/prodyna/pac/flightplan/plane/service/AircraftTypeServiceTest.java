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
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeErrorCode;
import com.prodyna.pac.flightplan.plane.exception.AircraftTypeValidationException;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;

/**
 * Arquillian test for {@link AircraftTypeService} methods.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class AircraftTypeServiceTest {

    @Inject
    private AircraftTypeService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Test
    @InSequence(10)
    public void loadExistingAircraftType() {
        AircraftType loadedAircraftType = service.loadAircraftTypeById("1");
        Assert.assertNotNull(loadedAircraftType);
        Assert.assertEquals("1", loadedAircraftType.getId());
        Assert.assertEquals("Type 1", loadedAircraftType.getDescription());
    }

    @Test
    @InSequence(15)
    public void testLoadAllAircraftTypes() {
        List<AircraftType> allAircraftTypes = service.loadAllAircraftTypes();
        Assert.assertEquals(3, allAircraftTypes.size());
    }

    @Test
    @InSequence(20)
    public void testCreateAircraftType() {
        AircraftType type = new AircraftType();
        type.setId(null);
        type.setDescription("test");
        type.setVersion(1);

        try {
            service.createAircraftType(type);
            Assert.fail("Created invalid aircraft type, but no exception was thrown.");
        } catch (TechnicalException e) {
            Assert.assertEquals(AircraftTypeErrorCode.ID_NOT_SET, e.getErrorCode());
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
    @InSequence(30)
    public void testAircraftDescriptionConflict() {
        AircraftType type = new AircraftType();
        type.setId("3");
        type.setDescription("Type 3");

        try {
            service.createAircraftType(type);
        } catch (AircraftTypeValidationException e) {
            Assert.fail("Caught unexpected exception");
        }

        type = new AircraftType();
        type.setId("4");
        type.setDescription("Type 2");

        try {
            service.createAircraftType(type);
            Assert.fail("Description is not unique. Creation must fail.");
        } catch (AircraftTypeValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(AircraftTypeErrorCode.DESCRIPTION_NOT_UNIQUE));
        }
    }

    @Test
    @InSequence(40)
    public void testUpdateAircraftType() {
        AircraftType type = service.loadAircraftTypeById("1");

        // Succeed: Type 3 does not exist until now
        type.setDescription("Type 3");
        try {
            type = service.updateAircraftType(type);
        } catch (AircraftTypeValidationException e) {
            Assert.fail("Caught unexpected exception!");
        }

        // Succeed: updating without any change must work as well.
        try {
            service.updateAircraftType(type);
        } catch (AircraftTypeValidationException e) {
            Assert.fail("Caught unexpected exception!");
        }

        type.setDescription("Type 2");
        try {
            service.updateAircraftType(type);
            Assert.fail("Updating to description 'Type 2' must fail, as this description already exists.");
        } catch (AircraftTypeValidationException e) {
            Assert.assertEquals(true, e.getErrorCodes().contains(AircraftTypeErrorCode.DESCRIPTION_NOT_UNIQUE));
        }
    }

    @Test
    @InSequence(50)
    public void testDeleteAircraftTypeById() {

        AircraftType type = service.loadAircraftTypeById("1000");
        Assert.assertNotNull(type);

        try {
            service.deleteAircraftTypeById("1000");
        } catch (AircraftTypeValidationException e) {
            Assert.fail("Caught unexpected exception.");
        }

        type = service.loadAircraftTypeById("1000");
        Assert.assertNull(type);

        try {
            service.deleteAircraftTypeById("3");
            Assert.fail("Deleting not existing AircraftType-Id should lead to an Exception!");
        } catch (AircraftTypeValidationException e) {
            Assert.assertEquals(true,
                    e.getErrorCodes().contains(AircraftTypeErrorCode.AIRCRAFTTYPE_TO_BE_DELETED_NOT_FOUND));
        }

    }

    @Test
    @InSequence(60)
    public void testIsAircraftTypeReferencedByPlanes() {
        boolean type1referenced = service.isAircraftTypeReferencedByPlanes("1");
        Assert.assertEquals(true, type1referenced);

        boolean type2referenced = service.isAircraftTypeReferencedByPlanes("2");
        Assert.assertEquals(true, type2referenced);

        boolean type1000referenced = service.isAircraftTypeReferencedByPlanes("1000");
        Assert.assertEquals(false, type1000referenced);
    }
}
