package com.prodyna.stream.backend.mongo.common;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.MongoClient;
import com.prodyna.stream.backend.mongo.Channel;
import com.prodyna.stream.backend.mongo.Like;
import com.prodyna.stream.backend.mongo.Tweet;
import com.prodyna.stream.backend.mongo.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.UnknownHostException;

/**
 * Produces a DB connection to MongoDB.
 */
@Named
public class MongoProducer {

    @Inject
    private Logger log;

    @Produces
    public DB createDBConnection() {
        try {
            DB db = MongoClient.connect(new DBAddress("stream"));
            return db;
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Unable to connect to database", e);
        }
    }

    @Produces
    public Datastore createDatastore() {
        DB db = createDBConnection();
        Morphia morphia = new Morphia();
        morphia.map(Channel.class);
        // morphia.map(Image.class);
        morphia.map(Like.class);
        morphia.map(Tweet.class);
        morphia.map(User.class);
        return morphia.createDatastore(db.getMongo(), db.getName());
    }

}