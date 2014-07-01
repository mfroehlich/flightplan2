package com.prodyna.stream.backend.mongo.service;

import com.prodyna.stream.backend.mongo.Channel;
import com.prodyna.stream.backend.mongo.ChannelService;
import com.prodyna.stream.backend.mongo.User;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.Secured;
import com.prodyna.stream.backend.mongo.common.security.UserDetails;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Secured
@Monitored
@Stateless
public class ChannelServiceBean implements ChannelService {

    @Inject
    private Logger log;

    @Inject
    private Datastore ds;

    @Inject
    private UserDetails userDetails;

    public List<Channel> getAllAvailableChannels() {
        return ds.find(Channel.class).asList();
    }

    public List<Channel> getSubscribedChannels() {
        return ds.get(User.class, userDetails.getUsername()).getSubscriptions();
    }

    public void subscribeChannel(long channelId) {
        Channel channel = ds.get(Channel.class, channelId);
        User userEntity = ds.get(User.class, userDetails.getUsername());
        userEntity.getSubscriptions().add(channel);
        ds.save(userEntity);
    }

    public void unsubscribeChannel(long channelId) {
        Channel channel = ds.get(Channel.class, channelId);
        User userEntity = ds.get(User.class, userDetails.getUsername());
        userEntity.getSubscriptions().remove(channel);
        ds.save(userEntity);
    }

    public void updateChannelSubscriptions(String channelIds) {
        // ok
    }

}
