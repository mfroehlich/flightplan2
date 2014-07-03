/**
 * 
 */
package com.prodyna.pac.flightplan.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionMapperImplTest {

    @Test
    public void testConvertToXMLString() {

        ExceptionMapperImpl test = new ExceptionMapperImpl();

        List<ErrorCode> errorCodes = new ArrayList<ErrorCode>();
        errorCodes.add(ApplicationErrorCode.OPTIMISTIC_LOCK_EXCEPTION);
        errorCodes.add(ApplicationErrorCode.TECHNICAL_PROBLEM);
        errorCodes.add(ApplicationErrorCode.USER_NOT_AUTHORIZED);

        String testString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<errorCodeWrapper>\n"
                + "    <errorCodes>application.optimistic_lock_exception</errorCodes>\n"
                + "    <errorCodes>application.technical_problem</errorCodes>\n"
                + "    <errorCodes>application.user_not_authorized</errorCodes>\n" + "</errorCodeWrapper>\n";

        ErrorCodeWrapper wrapper = new ErrorCodeWrapper();
        wrapper.setErrorCodes(errorCodes);

        String convertToXMLString = test.convertToXMLString(wrapper);

        Assert.assertEquals(testString, convertToXMLString);
    }
}
