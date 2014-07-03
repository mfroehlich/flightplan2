/**
 * 
 */
package com.prodyna.pac.flightplan.client;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.prodyna.pac.flightplan.client.messages.MessageReader;
import com.prodyna.pac.flightplan.common.exception.ErrorCodeWrapper;
import com.prodyna.pac.flightplan.common.exception.UserNotAuthorizedException;
import com.prodyna.pac.flightplan.user.exception.UserNotLoggedInException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * TODO mfroehlich Comment me
     * 
     * @param throwable
     */
    public void handleException(Throwable throwable) {

        logger.error("An error has occurred and is to be handled now: ", throwable);
        if (throwable instanceof RuntimeException) {
            RuntimeException ex = (RuntimeException) throwable;
            if (ex.getCause() instanceof InvocationTargetException) {
                InvocationTargetException targetEx = (InvocationTargetException) ex.getCause();

                Throwable realException = targetEx.getTargetException();
                if (realException instanceof BadRequestException) {
                    BadRequestException badRequestException = (BadRequestException) realException;
                    String message = badRequestException.getMessage();
                    Response response = badRequestException.getResponse();
                    Object entity = response.getEntity();
                    if (entity instanceof ByteArrayInputStream) {
                        ByteArrayInputStream stream = (ByteArrayInputStream) entity;
                        ErrorCodeWrapper wrapper = readWrapperFromEntityStream(stream);
                        String errorMessage = translateErrorCodes(wrapper);
                        Dialogs.create().title("An error occurred.")
                                .message("The following errors have occurred:\n\n" + errorMessage).showInformation();
                    }
                } else if (realException instanceof NotAuthorizedException) {
                    NotAuthorizedException target = (NotAuthorizedException) realException;
                    logger.error("User ist nicht autorisiert!!");
                    Dialogs.create().showException(target);
                } else if (realException instanceof UserNotLoggedInException) {
                    UserNotLoggedInException target = (UserNotLoggedInException) realException;
                    Dialogs.create().showException(target);
                } else if (realException instanceof UserNotAuthorizedException) {
                    UserNotAuthorizedException target = (UserNotAuthorizedException) realException;
                    Dialogs.create().showException(target);
                } else if (realException instanceof IllegalStateException) {
                    IllegalStateException target = (IllegalStateException) realException;
                    logger.error("Fehler beim Einlesen des FXML.");
                    Dialogs.create().showException(target);
                } else {
                    logger.debug("Displaying default exception");
                    Dialogs.create().showException(realException);
                }
            }
        }
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param entity
     * @return
     */
    private ErrorCodeWrapper readWrapperFromEntityStream(ByteArrayInputStream stream) {

        ErrorCodeWrapper errorCodeWrapper;
        try {
            InputSource src = new InputSource(stream);
            JAXBContext context = JAXBContext.newInstance(ErrorCodeWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            errorCodeWrapper = (ErrorCodeWrapper) um.unmarshal(src);
        } catch (JAXBException e) {
            logger.error("Error reading ErrorCodeWrapper from Response#Entity.", e);
            errorCodeWrapper = null;
        }

        return errorCodeWrapper;
    }

    private String translateErrorCodes(ErrorCodeWrapper wrapper) {
        StringBuilder s = new StringBuilder();
        for (String code : wrapper.getErrorCodes()) {
            String message = MessageReader.getMessage(code);
            s.append(message + "\n\n");
        }
        return s.toString();
    }
}
