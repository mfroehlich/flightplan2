/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.service;

import java.io.File;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@ArquilianSuiteDeployment
public class Deployments {

    @Deployment
    public static WebArchive createDeployment() {
        // File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve()
        // .withTransitivity().asFile();
        // for (File file : libs) {
        // try {
        // System.out.println(file.getCanonicalPath());
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        // archive.addAsLibraries(libs);
        // archive.addPackages(true, "com.prodyna.pac.flightplan.pilot");
        // archive.addPackages(true, "com.prodyna.pac.flightplan.monitoring");
        archive.addPackages(true, "com.prodyna.pac.flightplan");
        archive.addAsResource("persistence-test.xml", "META-INF/persistence.xml");
        archive.addAsWebInfResource(new File("src/main/resources/META-INF/beans.xml"), "beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }
}
