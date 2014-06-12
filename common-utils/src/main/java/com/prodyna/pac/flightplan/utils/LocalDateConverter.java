/**
 * 
 */
package com.prodyna.pac.flightplan.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class LocalDateConverter {

    public static LocalDate dateToLocalDate(final Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        return new Date(java.sql.Date.valueOf(localDate).getTime());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
}
