/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

import org.slf4j.Logger;

/**
 * TODO mfroehlich Comment me
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
                response = buildResponse(e, MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
            }
        } else if (thr instanceof ForbiddenException) {
            response = buildResponse(thr, MediaType.TEXT_PLAIN, Status.FORBIDDEN);
        } else {
            response = buildResponse(unwrapException(thr), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
        }

        logger.debug("****************************** CONVERTING EXCEPTION ********************************");
        thr.printStackTrace();
        logger.debug("created response: " + response);
        return response;
    }

    protected Response buildResponse(Object entity, String mediaType, Status status) {
        ResponseBuilder builder = Response.status(status);
        builder.header(VALIDATION_HEADER, "true");
        builder.type(MediaType.TEXT_PLAIN);
        builder.entity(entity);
        return builder.build();
    }

    protected Response buildErrorCodeResponse(ErrorCode errorCode, Status status) {
        Collection<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        errorCodes.add(errorCode);
        return buildErrorCodeResponse(errorCodes, status);
    }

    protected Response buildErrorCodeResponse(Collection<ErrorCode> errorCodes, Status status) {
        ResponseBuilder builder = Response.status(status);
        builder.header(VALIDATION_HEADER, "true");
        builder.type(MediaType.APPLICATION_XML);
        builder.entity(errorCodes);
        return builder.build();
    }

    protected String unwrapException(Throwable t) {
        StringBuffer sb = new StringBuffer();
        doUnwrapException(sb, t);
        return sb.toString();
    }

    private void doUnwrapException(StringBuffer sb, Throwable t) {
        if (t == null) {
            return;
        }
        sb.append(t.toString());
        if (t.getCause() != null && t != t.getCause()) {
            sb.append('[');
            doUnwrapException(sb, t.getCause());
            sb.append(']');
        }
    }

    private MediaType getAcceptMediaType(List<MediaType> accept) {
        Iterator<MediaType> it = accept.iterator();
        while (it.hasNext()) {
            MediaType mt = it.next();
            /*
             * application/xml media type causes an exception: org.jboss.resteasy.core.NoMessageBodyWriterFoundFailure:
             * Could not find MessageBodyWriter for response object of type:
             * org.jboss.resteasy.api.validation.ViolationReport of media type: application/xml
             */
            /*
             * if (MediaType.APPLICATION_XML_TYPE.getType().equals(mt.getType()) &&
             * MediaType.APPLICATION_XML_TYPE.getSubtype().equals(mt.getSubtype())) { return
             * MediaType.APPLICATION_XML_TYPE; }
             */
            if (MediaType.APPLICATION_JSON_TYPE.getType().equals(mt.getType())
                    && MediaType.APPLICATION_JSON_TYPE.getSubtype().equals(mt.getSubtype())) {
                return MediaType.APPLICATION_JSON_TYPE;
            }
        }
        return null;
    }
}
