package com.prodyna.stream.backend.mongo.common.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * A request scoped injectable bean that contains the credentials. It will be written by a JAX-RS specific Interceptor on each request.
 */
@Named
@RequestScoped
public class UserCredentials {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
