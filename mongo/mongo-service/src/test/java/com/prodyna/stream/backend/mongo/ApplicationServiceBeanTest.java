package com.prodyna.stream.backend.mongo;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class ApplicationServiceBeanTest {

    @Inject
    private Logger log;

    @Inject
    private ScenarioSupporterBean scenario;

    @Inject
    private ApplicationService applicationService;

    @Test
    @InSequence(1)
    public void resetScneario() {
        scenario.resetScenario();
    }

    @Test
    @InSequence(2)
    public void applicationInfo() {
        Assert.assertEquals("read-from-scenario", applicationService.getApplicationInfo().getName());
    }

}