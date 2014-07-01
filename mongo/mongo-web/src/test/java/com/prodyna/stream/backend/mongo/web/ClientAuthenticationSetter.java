package com.prodyna.stream.backend.mongo.web;

import com.mongodb.util.Base64Codec;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

public class ClientAuthenticationSetter implements ClientRequestFilter {

    private String auth;

    public ClientAuthenticationSetter(String username, String password) {
        auth = "Basic " + new Base64Codec().encode(String.format("%s:%s", username, password).getBytes());
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", auth);
    }

}
