package com.prodyna.stream.backend.mongo;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

@ArquilianSuiteDeployment
public class Deployments {

    @Deployment
    public static Archive<?> createDeployment() {
        File[] mpc = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();
        for (File f : mpc) {
            System.out.println("Found dependency: " + f);
        }

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        archive.addPackages(true, "com.prodyna.stream.backend.mongo");
        archive.addAsLibraries(mpc);
        archive.addAsWebInfResource(new File("src/main/resources/META-INF/beans.xml"), "beans.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

}
