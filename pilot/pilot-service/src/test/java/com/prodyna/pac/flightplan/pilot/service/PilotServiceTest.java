/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.scenario.Scenario1;
import com.prodyna.pac.flightplan.scenario.ScenarioPreparer;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Ignore
@RunWith(Arquillian.class)
public class PilotServiceTest {

    @Inject
    private PilotService service;

    @Inject
    private ScenarioPreparer scenarioPreparer;

    @Before
    public void setupDatabase() throws Exception {
        scenarioPreparer.executeSqlFile(new Scenario1());
    }

    @Deployment
    public static WebArchive createDeployment() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve()
                .withoutTransitivity().asFile();

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        archive.addPackages(true, "com.prodyna.pac.flightplan");
        archive.addAsLibraries(libs);
        // archive.addAsResource("persistence-test.xml", "META-INF/persistence.xml");
        archive.addAsWebInfResource(new File("src/main/resources/META-INF/beans.xml"), "beans.xml");

        return archive;
    }

    @Test
    @InSequence(10)
    public void test1() {
        Pilot loadedPilot = service.loadPilotById("1");
        Assert.assertNotNull(loadedPilot);

        Pilot notExistingPilot = service.loadPilotById("not_existing_id");
        Assert.assertNull(notExistingPilot);
    }
}
