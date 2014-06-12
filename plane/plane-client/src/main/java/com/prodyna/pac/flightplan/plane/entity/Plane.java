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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.reservation.entity.ReservationItem;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "plane", schema = "flightplan")
@PrimaryKeyJoinColumn(name = "id")
@NamedQueries({ @NamedQuery(name = Plane.QUERY_LOAD_ALL_PLANES, query = "FROM Plane ORDER BY name") })
public class Plane extends ReservationItem implements Serializable {

    private static final long serialVersionUID = 7128736888545719542L;

    public static final String QUERY_LOAD_ALL_PLANES = "load_all_planes";

    @Column(name = "name")
    private String name;

    @Column(name = "numberplate")
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "aircrafttype")
    private AircraftType aircraftType;

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
