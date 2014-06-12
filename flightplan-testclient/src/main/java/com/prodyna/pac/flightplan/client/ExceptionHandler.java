/**
 * 
 */
package com.prodyna.pac.flightplan.client;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.NotAuthorizedException;

import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.common.exception.TechnicalException;
import com.prodyna.pac.flightplan.common.exception.UserNotAuthorizedException;
import com.prodyna.pac.flightplan.common.exception.UserNotLoggedInException;

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

                if (targetEx.getTargetException() instanceof TechnicalException) {
                    TechnicalException target = (TechnicalException) targetEx.getTargetException();
                    // TODO mfroehlich Da geht doch mehr, oder?
                    Dialogs.create().showException(target);
                } else if (targetEx.getTargetException() instanceof NotAuthorizedException) {
                    NotAuthorizedException target = (NotAuthorizedException) targetEx.getTargetException();
                    logger.error("User ist nicht autorisiert!!");
                    Dialogs.create().showException(target);
                } else if (targetEx.getTargetException() instanceof UserNotLoggedInException) {
                    UserNotLoggedInException target = (UserNotLoggedInException) targetEx.getTargetException();
                    Dialogs.create().showException(target);
                } else if (targetEx.getTargetException() instanceof UserNotAuthorizedException) {
                    UserNotAuthorizedException target = (UserNotAuthorizedException) targetEx.getTargetException();
                    Dialogs.create().showException(target);
                } else if (targetEx.getTargetException() instanceof IllegalStateException) {
                    IllegalStateException target = (IllegalStateException) targetEx.getTargetException();
                    logger.error("Fehler beim Einlesen des FXML.");
                    Dialogs.create().showException(target);
                } else {
                    logger.debug("Displaying default exception");
                    Dialogs.create().showException(targetEx.getTargetException());
                }
            }
        }
    }
}
