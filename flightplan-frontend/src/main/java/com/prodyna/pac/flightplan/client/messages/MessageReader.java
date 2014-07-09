/**
 * 
 */
package com.prodyna.pac.flightplan.client.messages;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.utils.StringUtils;

/**
 * Translates error codes and other message codes into human readable test messages.
 * 
 * @author mfroehlich
 *
 */
public class MessageReader {

    private static final Logger logger = LoggerFactory.getLogger(MessageReader.class);

    private static ResourceBundle resources = ResourceBundle.getBundle("messages.messages", new Locale("en"));

    /**
     * Get the value for the given message code from the current resource file.
     * 
     * @param messageCode The message code to get the value for.
     * @return The value for the given message code.
     */
    public static String getMessage(String messageCode, String... params) {
        String value;
        try {
            value = resources.getString(messageCode);

            if (params != null) {
                int i = 0;
                for (String param : params) {
                    value = value.replace("{" + i++ + "}", StringUtils.checkForNull(param, ""));
                }
            }
        } catch (Exception ex) {
            value = "Message not found for original code " + messageCode;
            logger.debug("Could not find a value for the message code " + messageCode, ex);
        }

        if (StringUtils.trim(value, null) == null) {
            value = messageCode + " (!! missing translation !!)";
        }

        return value;
    }
}
