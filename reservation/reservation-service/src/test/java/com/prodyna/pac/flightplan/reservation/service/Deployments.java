/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.service;

import java.io.File;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

@ArquilianSuiteDeployment
public class Deployments {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class);
        archive.addPackages(true, "com.prodyna.pac.flightplan");
        archive.addAsResource("persistence-test.xml", "META-INF/persistence.xml");
        archive.addAsResource(new File("src/main/resources/META-INF/beans.xml"), "META-INF/beans.xml");

        return archive;
    }
}
