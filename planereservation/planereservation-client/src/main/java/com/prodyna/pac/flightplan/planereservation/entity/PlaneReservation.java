/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.pilot.entity.Pilot;
import com.prodyna.pac.flightplan.plane.entity.Plane;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;

/**
 * Entity object representing a concrete reservation for planes.
 * 
 * @author mfroehlich
 *
 */
@XmlRootElement
@Entity
@Table(name = "reservation")
@NamedQueries({
        @NamedQuery(name = PlaneReservation.QUERY_LOAD_ALL_PLANERESERVATIONS, query = "FROM PlaneReservation"),
        @NamedQuery(
                name = PlaneReservation.QUERY_LOAD_PLANERESERVATIONS_BY_PLANE_AND_DATE,
                query = "FROM PlaneReservation WHERE plane.id = :planeId AND ( (startTime between :dateStartOfDay AND :dateEndOfDay) OR (endTime between :dateStartOfDay2 AND :dateEndOfDay2) ) AND status IN :statusList ORDER BY startTime"),
        @NamedQuery(
                name = PlaneReservation.QUERY_LOAD_PLANERESERVATIONS_BY_PILOTID,
                query = "FROM PlaneReservation WHERE pilot.id = :userId ORDER BY startTime") })
public class PlaneReservation {

    public static final String QUERY_LOAD_ALL_PLANERESERVATIONS = "load_all_planereservations";
    public static final String QUERY_LOAD_PLANERESERVATIONS_BY_PLANE_AND_DATE = "load_planereservations_by_plane_and_date";
    public static final String QUERY_LOAD_PLANERESERVATIONS_BY_PILOTID = "load_planereservations_by_pilotid";

    @Id
    private String id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Pilot pilot;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Plane plane;

    @Column(name = "start")
    private Date startTime;

    @Column(name = "end")
    private Date endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    /**
     * @return the id
     */
    @XmlElement
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the pilot
     */
    @XmlElement
    public Pilot getPilot() {
        return pilot;
    }

    /**
     * @param pilot the pilot to set
     */
    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    /**
     * @return the plane
     */
    @XmlElement
    public Plane getPlane() {
        return plane;
    }

    /**
     * @param plane the plane to set
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    /**
     * @return the startTime
     */
    @XmlElement
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    @XmlElement
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the status
     */
    @XmlElement
    public ReservationStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
