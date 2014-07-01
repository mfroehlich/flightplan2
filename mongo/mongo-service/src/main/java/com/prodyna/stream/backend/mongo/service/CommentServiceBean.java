package com.prodyna.stream.backend.mongo.service;

import com.prodyna.stream.backend.mongo.CommentService;
import com.prodyna.stream.backend.mongo.common.monitoring.Monitored;
import com.prodyna.stream.backend.mongo.common.security.Secured;

import javax.ejb.Stateless;

@Secured
@Monitored
@Stateless
public class CommentServiceBean implements CommentService {

    @Override
    public void addCommentLike(String commentId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCommentLike(String commentId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void readTweetLikes(String commentId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
