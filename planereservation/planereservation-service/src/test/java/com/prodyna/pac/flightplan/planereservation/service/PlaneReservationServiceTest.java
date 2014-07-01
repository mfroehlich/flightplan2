/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Ignore
@RunWith(Arquillian.class)
public class PlaneReservationServiceTest {

    @Inject
    private PlaneReservationService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class);
        archive.addPackages(true, "com.prodyna.pac.flightplan");
        archive.addAsResource("persistence-test.xml", "META-INF/persistence.xml");
        archive.addAsResource(new File("src/main/resources/META-INF/beans.xml"), "META-INF/beans.xml");

        return archive;
    }

    @Test
    @InSequence(10)
    public void testLoadAllPlaneReservations() {
        List<PlaneReservation> loadedAllPlaneReservations = service.loadAllPlaneReservations();
        Assert.assertEquals(6, loadedAllPlaneReservations.size());
    }

    @Test
    @InSequence(20)
    public void testLoadPlaneReservationByPlaneAndDate() {
        long timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 4)).getTime();
        List<PlaneReservation> loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("1", timeInMillis);
        Assert.assertEquals(3, loadedPlaneReservations.size());

        timeInMillis = LocalDateConverter.localDateToDate(LocalDate.of(2014, 7, 5)).getTime();
        loadedPlaneReservations = service.loadPlaneReservationsByPlaneAndDate("1", timeInMillis);
        Assert.assertEquals(0, loadedPlaneReservations.size());
    }
}
