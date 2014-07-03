/**
 * 
 */
package com.prodyna.pac.flightplan.client;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
import com.prodyna.pac.flightplan.common.exception.ApplicationErrorCode;
import com.prodyna.pac.flightplan.common.exception.ErrorCode;
import com.prodyna.pac.flightplan.common.exception.ErrorCodeWrapper;
import com.prodyna.pac.flightplan.common.exception.FunctionalException;
import com.prodyna.pac.flightplan.common.exception.TechnicalException;
import com.prodyna.pac.flightplan.user.exception.UserErrorCode;

/**
 * Helper class to display error messages depending on the occurring exception.
 * 
 * @author mfroehlich
 *
 */
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Transform the specified exception into an error message and display it in an info dialog.
     * 
     * @param throwable
     */
    public void handleException(Throwable throwable) {

        logger.error("An error has occurred. An error message will be displayed now. Exception: ", throwable);

        String errorMessage;

        if (throwable instanceof RuntimeException) {
            RuntimeException ex = (RuntimeException) throwable;
            if (ex.getCause() instanceof InvocationTargetException) {
                InvocationTargetException targetEx = (InvocationTargetException) ex.getCause();

                Throwable realException = targetEx.getTargetException();
                if (realException instanceof BadRequestException) {
                    BadRequestException badRequestException = (BadRequestException) realException;
                    Response response = badRequestException.getResponse();
                    Object entity = response.getEntity();
                    ByteArrayInputStream stream = (ByteArrayInputStream) entity;
                    ErrorCodeWrapper wrapper = readWrapperFromEntityStream(stream);
                    errorMessage = translateErrorCodes(wrapper);
                } else if (realException instanceof TechnicalException) {
                    TechnicalException target = (TechnicalException) realException;
                    errorMessage = translateErrorCode(target.getErrorCode());
                } else if (realException instanceof FunctionalException) {
                    FunctionalException target = (FunctionalException) realException;
                    errorMessage = translateErrorCodes(target.getErrorCodes());
                } else if (realException instanceof NotAuthorizedException) {
                    errorMessage = translateErrorCode(UserErrorCode.USER_NOT_AUTHORIZED);
                } else {
                    errorMessage = translateErrorCode(ApplicationErrorCode.TECHNICAL_PROBLEM);
                }
            } else {
                errorMessage = translateErrorCode(ApplicationErrorCode.TECHNICAL_PROBLEM);
            }
        } else {
            errorMessage = translateErrorCode(ApplicationErrorCode.TECHNICAL_PROBLEM);
        }

        showErrorDialog(errorMessage);
    }

    private void showErrorDialog(String message) {
        Dialogs.create().title("An error occurred.").message("The following error(s) occurred:\n\n" + message)
                .showInformation();
    }

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

    private String translateErrorCodes(List<ErrorCode> errorCodes) {
        StringBuilder s = new StringBuilder();
        errorCodes.forEach(code -> s.append(translateErrorCode(code)));
        s.append("\n\n");
        return s.toString();
    }

    private String translateErrorCode(ErrorCode code) {
        String message = MessageReader.getMessage(code.getCode());
        return message;
    }
}
