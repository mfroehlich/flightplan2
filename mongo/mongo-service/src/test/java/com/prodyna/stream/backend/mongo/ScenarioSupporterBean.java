package com.prodyna.stream.backend.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
public class ScenarioSupporterBean {

    @Inject
    private Logger log;
    @Inject
    private DB db;

    @GET
    @Path("/reset")
    public void resetScenario() {
        log.warn("Resetting scenario");

        DBCollection meta = db.getCollection("meta");

        meta.drop();
        meta.insert(new BasicDBObject(new BasicDBObject("_id", "name").append("value", "read-from-scenario")));
    }
}