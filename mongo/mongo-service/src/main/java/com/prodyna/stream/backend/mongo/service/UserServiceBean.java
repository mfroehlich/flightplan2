package com.prodyna.stream.backend.mongo.service;

import com.prodyna.stream.backend.mongo.User;
import com.prodyna.stream.backend.mongo.UserService;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.Secured;

import javax.ejb.Stateless;
import java.util.List;

@Secured
@Monitored
@Stateless
public class UserServiceBean implements UserService {

    @Override
    public List<User> getUserInfos(String userIds) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] getUserAvatar(long userId) {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

}
