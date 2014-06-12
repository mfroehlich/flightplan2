/**
 * 
 */
package com.prodyna.pac.flightplan.reservation.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
public enum ReservationStatus {

    RESERVED, CANCELLED, LENT, RETURNED;
}
