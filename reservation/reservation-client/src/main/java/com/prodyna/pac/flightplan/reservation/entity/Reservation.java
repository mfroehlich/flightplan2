package com.prodyna.pac.flightplan.reservation.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.user.entity.User;

/**
 * Entity object representing a reservation.
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "reservation")
@NamedQueries(
        value = {
                @NamedQuery(
                        name = Reservation.QUERY_UPDATE_RESERVATIONSTATUS_BY_ID,
                        query = "UPDATE Reservation SET status = :status, version = (version + 1) WHERE id = :id "),
                @NamedQuery(
                        name = Reservation.QUERY_LOAD_OVERDUE_RESERVATIONIDS,
                        query = "SELECT id FROM Reservation r WHERE r.end < :now AND r.status = :statusLent "),
                @NamedQuery(
                        name = Reservation.QUERY_FIND_RESERVATION_CONFLICTS,
                        query = "SELECT id FROM Reservation r WHERE r.end > :start AND r.start < :end AND r.status in (:statusLent, :statusReserved) AND r.item = :item AND r.id <> :id ") })
public class Reservation implements Serializable {

    public static final String QUERY_UPDATE_RESERVATIONSTATUS_BY_ID = "update_reservation";
    public static final String QUERY_LOAD_OVERDUE_RESERVATIONIDS = "load_overdue_reservationids";
    public static final String QUERY_FIND_RESERVATION_CONFLICTS = "query_find_reservation_conflicts";

    public static final String PROP_ID = "id";
    public static final String PROP_USER = "user";
    public static final String PROP_RESERVATION_ITEM = "item";
    public static final String PROP_START = "start";
    public static final String PROP_END = "end";
    public static final String PROP_RESERVATION_STATUS = "status";

    private static final long serialVersionUID = 1383729821481338652L;

    public Reservation() {
    }

    @NotNull
    @Size(min = 1, max = 50)
    @Id
    private String id;

    @Version
    private int version;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private ReservationItem item;

    @NotNull
    @Column(name = "start")
    private Date start;

    @NotNull
    @Column(name = "end")
    private Date end;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @XmlElement
    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlElement
    public ReservationItem getItem() {
        return item;
    }

    public void setItem(ReservationItem item) {
        this.item = item;
    }

    @XmlElement
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @XmlElement
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reservation other = (Reservation) obj;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (item == null) {
            if (other.item != null)
                return false;
        } else if (!item.equals(other.item))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        if (status != other.status)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", user=" + user + ", item=" + item + ", start=" + start + ", end=" + end
                + ", status=" + status + "]";
    }
}
