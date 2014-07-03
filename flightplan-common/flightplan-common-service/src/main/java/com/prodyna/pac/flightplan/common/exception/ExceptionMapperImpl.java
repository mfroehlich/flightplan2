/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;

/**
 * Implementation of {@link ExceptionMapper} to map Exception-Types to HTTP-Response-Codes for sending them to the
 * client via REST.
 * 
 * @author mfroehlich
 *
 */
@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Throwable> {
    public static final String VALIDATION_HEADER = "ABC";

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Throwable thr) {
        Response response;

        logger.debug("****************************** CONVERTING EXCEPTION ********************************");
        logger.debug("Exception to be converted: " + thr);

        if (thr instanceof TechnicalException) {
            TechnicalException e = (TechnicalException) thr;
            response = buildErrorCodeResponse(e.getErrorCode(), Status.BAD_REQUEST);
        } else if (thr instanceof FunctionalException) {
            FunctionalException e = (FunctionalException) thr;
            response = buildErrorCodeResponse(e.getErrorCodes(), Status.BAD_REQUEST);
        } else if (thr instanceof EJBTransactionRolledbackException) {
            EJBTransactionRolledbackException e = (EJBTransactionRolledbackException) thr;
            if (e.getCause() instanceof OptimisticLockException) {
                response = buildErrorCodeResponse(ApplicationErrorCode.OPTIMISTIC_LOCK_EXCEPTION, Status.BAD_REQUEST);
            } else {
                response = buildErrorCodeResponse(ApplicationErrorCode.TECHNICAL_PROBLEM, Status.BAD_REQUEST);
            }
        } else if (thr instanceof ForbiddenException) {
            response = buildErrorCodeResponse(ApplicationErrorCode.USER_NOT_AUTHORIZED, Status.BAD_REQUEST);
        } else {
            response = buildErrorCodeResponse(ApplicationErrorCode.TECHNICAL_PROBLEM, Status.INTERNAL_SERVER_ERROR);
        }

        logger.debug("created response: " + response);
        return response;
    }

    protected Response buildErrorCodeResponse(ErrorCode errorCode, Status status) {
        List<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        errorCodes.add(errorCode);
        return buildErrorCodeResponse(errorCodes, status);
    }

    protected Response buildErrorCodeResponse(List<ErrorCode> errorCodes, Status status) {

        logger.debug("Creating a response object with " + errorCodes.size() + " error codes");
        errorCodes.forEach(code -> logger.debug("  ERROR CODE: " + code.getCode()));

        ErrorCodeWrapper wrapper = new ErrorCodeWrapper();
        wrapper.setErrorCodes(errorCodes);
        String errorCodesAsXmlString = convertToXMLString(wrapper);

        ResponseBuilder builder = Response.status(status);
        builder.header(VALIDATION_HEADER, "true");
        builder.type(MediaType.APPLICATION_XML);
        builder.entity(errorCodesAsXmlString);

        return builder.build();
    }

    public String convertToXMLString(ErrorCodeWrapper wrapper) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext ctx = JAXBContext.newInstance(ErrorCodeWrapper.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, writer);
        } catch (JAXBException e) {
            logger.error("Error converting ErrorCodes to XML!", e);
        }
        return writer.toString();
    }

    // protected String unwrapException(Throwable t) {
    // StringBuffer sb = new StringBuffer();
    // doUnwrapException(sb, t);
    // return sb.toString();
    // }
    //
    // private void doUnwrapException(StringBuffer sb, Throwable t) {
    // if (t == null) {
    // return;
    // }
    // sb.append(t.toString());
    // if (t.getCause() != null && t != t.getCause()) {
    // sb.append('[');
    // doUnwrapException(sb, t.getCause());
    // sb.append(']');
    // }
    // }
    //
    // private MediaType getAcceptMediaType(List<MediaType> accept) {
    // Iterator<MediaType> it = accept.iterator();
    // while (it.hasNext()) {
    // MediaType mt = it.next();
    // /*
    // * application/xml media type causes an exception: org.jboss.resteasy.core.NoMessageBodyWriterFoundFailure:
    // * Could not find MessageBodyWriter for response object of type:
    // * org.jboss.resteasy.api.validation.ViolationReport of media type: application/xml
    // */
    // /*
    // * if (MediaType.APPLICATION_XML_TYPE.getType().equals(mt.getType()) &&
    // * MediaType.APPLICATION_XML_TYPE.getSubtype().equals(mt.getSubtype())) { return
    // * MediaType.APPLICATION_XML_TYPE; }
    // */
    // if (MediaType.APPLICATION_JSON_TYPE.getType().equals(mt.getType())
    // && MediaType.APPLICATION_JSON_TYPE.getSubtype().equals(mt.getSubtype())) {
    // return MediaType.APPLICATION_JSON_TYPE;
    // }
    // }
    // return null;
    // }
}
