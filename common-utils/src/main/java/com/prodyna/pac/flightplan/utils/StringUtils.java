/**
 * 
 */
package com.prodyna.pac.flightplan.utils;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class StringUtils {
    public static String checkForNull(String s, String defaultIfNullOrEmpty) {
        String result;
        if (s == null || s.isEmpty()) {
            result = defaultIfNullOrEmpty;
        } else {
            result = s;
        }
        return result;
    }

    public static String trim(String s, String defaultIfNullOrEmpty) {
        String result;
        if (checkForNull(s, null) == null) {
            result = defaultIfNullOrEmpty;
        } else {
            result = s.trim();
        }
        return result;
    }
}
