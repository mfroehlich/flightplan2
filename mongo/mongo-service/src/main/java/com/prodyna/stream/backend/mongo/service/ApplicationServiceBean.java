package com.prodyna.stream.backend.mongo.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.prodyna.stream.backend.mongo.Application;
import com.prodyna.stream.backend.mongo.ApplicationService;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.UserDetails;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
// @Secured
@Monitored
public class ApplicationServiceBean implements ApplicationService {

    @Inject
    private Logger log;

    @Inject
    private DB db;

    @Inject
    private UserDetails userDetails;

    @Override
    public Application getApplicationInfo() {
        DBObject object = db.getCollection("meta").findOne(new BasicDBObject("_id", "name"));
        Application info = new Application(object.get("value").toString());
        log.info("Called as " + userDetails.getUsername());
        log.info("Providing information " + info);
        return info;
    }

    @Override
    public byte[] getApplicationIcon() {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] getApplicationLogo() {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

}
