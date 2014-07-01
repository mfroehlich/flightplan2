package com.prodyna.stream.backend.mongo.web;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquilianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

@ArquilianSuiteDeployment
public class Deployments {

    @Deployment(testable = false)
    public static org.jboss.shrinkwrap.api.Archive<?> createDeployment() {
        File[] mpc = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();
        for (File f : mpc) {
            System.out.println("Found dependency: " + f);
        }

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(RESTApplication.class, AuthenticationFilter.class, LoggingFilter.class)
                .addClasses(ScenarioSupporterBean.class)
                .addAsLibraries(mpc)
                .addAsWebInfResource(new File("../mongo-service/src/main/resources/META-INF/beans.xml"), "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

}
