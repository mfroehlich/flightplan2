/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.plane.service.AircraftTypeService;
import com.prodyna.pac.flightplan.rest.AbstractRestTest;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * Arquillian REST test for calling the {@link PilotService} via REST.
 * 
 * @author mfroehlich
 *
 */
@RunWith(Arquillian.class)
public class PilotServiceRestTest extends AbstractRestTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        archive.addPackages(true, "com.prodyna.pac.flightplan");
        archive.addAsResource("persistence-test.xml", "META-INF/persistence.xml");
        archive.addAsResource(new File("src/test/resources/META-INF/beans.xml"), "META-INF/beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }

    @Test
    @RunAsClient
    @InSequence(1)
    public void testDeleteUserBeforeTesting() {
        PilotService pilotService = createService(PilotService.class);
        try {
            pilotService.deletePilotById("12345");
        } catch (Exception e) {
            // Never mind, if it fails, the pilot does not exist...
        }
    }

    @Test
    @RunAsClient
    @InSequence(1000)
    public void testCreatePilot() {
        PilotService pilotService = createService(PilotService.class);
        Pilot pilot = new Pilot();
        pilot.setId("12345");
        pilot.setUserName("Eindeutiger Username");
        pilot.setFirstName("Markus");
        pilot.setLastName("Fröhlich");
        pilot.seteMailAddress("mfroehlich@prodyna.com");
        pilot.setPassword("test");

        Date licenceValidity = generateDate(2014, 12, 12, 0, 0, 0);
        pilot.setLicenceValidity(licenceValidity);

        try {
            // NOTE: AircraftType is null!!
            pilotService.createPilot(pilot);
            Assert.fail("Exception expected.");
        } catch (BadRequestException e) {
            // This catch block should catch the exception.
        } catch (Exception e) {
            Assert.fail("unexpected exception.");
        }

        AircraftType type = new AircraftType();
        type.setId("12345");
        type.setDescription("Eindeutiger AircraftType");
        try {
            AircraftTypeService aircraftTypeSevice = createService(AircraftTypeService.class);
            aircraftTypeSevice.createAircraftType(type);
        } catch (Exception e) {
            // Never mind, if it fails, the Aircrafttype already exists...
        }

        List<AircraftType> types = new ArrayList<>();
        types.add(type);
        pilot.setAssignedAircraftTypes(types);

        try {

            Pilot createdPilot = pilotService.createPilot(pilot);

            Assert.assertEquals(0, createdPilot.getVersion());
            Assert.assertEquals("12345", createdPilot.getId());
            Assert.assertEquals("Eindeutiger Username", createdPilot.getUserName());
            Assert.assertEquals("Markus", createdPilot.getFirstName());
            Assert.assertEquals("Fröhlich", createdPilot.getLastName());
            Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", createdPilot.getPassword());
            Assert.assertEquals("mfroehlich@prodyna.com", createdPilot.geteMailAddress());
            Assert.assertEquals(licenceValidity, createdPilot.getLicenceValidity());
            Assert.assertEquals(1, createdPilot.getAssignedAircraftTypes().size());
        } catch (Exception e) {
            Assert.fail("unexpected exception.");
        }
    }

    @Test
    @RunAsClient
    @InSequence(2000)
    public void testLoadPilotById() {
        PilotService pilotService = createService(PilotService.class);
        try {
            Pilot loadedPilot = pilotService.loadPilotById("12345");

            Assert.assertEquals(0, loadedPilot.getVersion());
            Assert.assertEquals("12345", loadedPilot.getId());
            Assert.assertEquals("Eindeutiger Username", loadedPilot.getUserName());
            Assert.assertEquals("Markus", loadedPilot.getFirstName());
            Assert.assertEquals("Fröhlich", loadedPilot.getLastName());
            Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", loadedPilot.getPassword());
            Assert.assertEquals("mfroehlich@prodyna.com", loadedPilot.geteMailAddress());
            Assert.assertEquals(generateDate(2014, 12, 12, 0, 0, 0), loadedPilot.getLicenceValidity());
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    @Test
    @RunAsClient
    @InSequence(3000)
    public void testDeletePilotById() {
        PilotService pilotService = createService(PilotService.class);
        try {
            pilotService.deletePilotById("12345");
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    private Date generateDate(int year, int month, int day, int hour, int min, int sec) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime startTime = LocalTime.of(hour, min, sec);
        LocalDateTime localStartDate = LocalDateTime.of(date, startTime);
        return LocalDateConverter.localDateTimeToDate(localStartDate);
    }
}
