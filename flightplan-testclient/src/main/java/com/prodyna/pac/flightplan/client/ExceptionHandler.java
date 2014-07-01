/**
 * 
 */
package com.prodyna.pac.flightplan.client;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.common.exception.FunctionalException;
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
                    Dialogs.create().title("An error occurred.")
                            .message("Error message: " + message + "\n\nYou might have made an error...")
                            .showInformation();
                } else if (realException instanceof FunctionalException) {
                    FunctionalException target = (FunctionalException) realException;
                    // TODO mfroehlich Da geht doch mehr, oder?
                    Dialogs.create().showException(target);
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
}
