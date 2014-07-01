package com.prodyna.stream.backend.mongo.common.security;

/**
 * Created with IntelliJ IDEA.
 * User: dkrizic
 * Date: 02.01.14
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class NotAuthorizedException extends Exception {

    public NotAuthorizedException(String message) {
        super(message);
    }

}
