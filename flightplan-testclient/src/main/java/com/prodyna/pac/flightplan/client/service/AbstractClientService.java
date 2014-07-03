/**
 * 
 */
package com.prodyna.pac.flightplan.client.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.controlsfx.dialog.Dialogs.UserInfo;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.session.SessionManager;

/**
 * Superclass of REST-services containing logic to instantiate a service-class with header information containing the
 * username and password of the logged-in user for authentication against the backend.
 * 
 * @author mfroehlich
 *
 */
public abstract class AbstractClientService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractClientService.class);

    private static final String authorizationHeaderKey = "Authorization";

    /**
     * TODO mfroehlich Server-URI in config-file auslagern
     */
    private static final String serverUri = "http://localhost:8080/flightplancontext/restapp/";

    /**
     * Create an instance of the specified REST-Service-class with header information containing the username and
     * password of the logged-in user so that REST-service-calls will be successfully authenticated against the backend.
     * 
     * 
     * @param restServiceClass
     * @return
     */
    protected <C> C createRestService(Class<C> restServiceClass) {

        WebTarget baseRestTarget = getBaseRestTarget();
        MultivaluedMap<String, Object> headers = generateHeaders();
        List<Cookie> cookies = new ArrayList<Cookie>();
        Form form = new Form();
        C newResource = WebResourceFactory.newResource(restServiceClass, baseRestTarget, false, headers, cookies, form);
        return newResource;
    }

    private WebTarget getBaseRestTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(serverUri);
        return target;
    }

    private MultivaluedMap<String, Object> generateHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        try {
            UserInfo userInfo = SessionManager.getInstance().getLoggedUserInfo();
            if (userInfo != null) {
                String token = userInfo.getUserName() + ":" + userInfo.getPassword();
                String headerValue = "Basic "
                        + javax.xml.bind.DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
                headers.add(authorizationHeaderKey, headerValue);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Error adding user credentials to request header.", e);
        }

        return headers;
    }
}
