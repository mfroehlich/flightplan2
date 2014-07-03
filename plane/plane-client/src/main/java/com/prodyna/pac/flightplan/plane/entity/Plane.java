/**
 * 
 */
package com.prodyna.pac.flightplan.plane.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.reservation.entity.ReservationItem;

/**
 * Entity object representing a plane.
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "plane")
@PrimaryKeyJoinColumn(name = "id")
@NamedQueries({
        @NamedQuery(name = Plane.QUERY_LOAD_ALL_PLANES, query = "FROM Plane ORDER BY name"),
        @NamedQuery(
                name = Plane.QUERY_COUNT_PLANES_REFERENCING_AIRCRAFTTYPE,
                query = "SELECT count(*) FROM Plane WHERE aircraftType.id = :aircraftTypeId"),
        @NamedQuery(
                name = Plane.QUERY_CHECK_NAME_AND_NUMBERPLATE_UNIQUE,
                query = "SELECT COUNT(*) FROM Plane WHERE id <> :id AND (name = :name OR numberPlate = :numberPlate) ") })
public class Plane extends ReservationItem implements Serializable {

    private static final long serialVersionUID = 7128736888545719542L;

    public static final String QUERY_LOAD_ALL_PLANES = "load_all_planes";
    public static final String QUERY_COUNT_PLANES_REFERENCING_AIRCRAFTTYPE = "count_planes_referencing_aircrafttype";
    public static final String QUERY_CHECK_NAME_AND_NUMBERPLATE_UNIQUE = "query_check_name_and_numberplate_unique";

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_NUMBERPLATE = "numberPlate";
    public static final String PROP_AIRCRAFTTYPE = "aircraftType";

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numberplate", unique = true)
    private String numberPlate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "aircrafttype")
    private AircraftType aircraftType;

    public Plane() {
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    @XmlElement
    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((aircraftType == null) ? 0 : aircraftType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((numberPlate == null) ? 0 : numberPlate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plane other = (Plane) obj;
        if (aircraftType == null) {
            if (other.aircraftType != null)
                return false;
        } else if (!aircraftType.equals(other.aircraftType))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (numberPlate == null) {
            if (other.numberPlate != null)
                return false;
        } else if (!numberPlate.equals(other.numberPlate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Plane [name=" + name + ", numberPlate=" + numberPlate + ", aircraftType=" + aircraftType + ", getId()="
                + getId() + "]";
    }
}
