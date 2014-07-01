package com.prodyna.stream.backend.mongo.service;

import com.mongodb.DB;
import com.prodyna.stream.backend.mongo.MediaService;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.Secured;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Secured
@Monitored
@Stateless
public class MediaServiceBean implements MediaService {

    @Inject
    private Logger log;
    @Inject
    private DB db;

    public byte[] getMedia(long id, Long width) {
        log.info("Request for id " + id);
        return "Hello, World".getBytes();
    }

}
