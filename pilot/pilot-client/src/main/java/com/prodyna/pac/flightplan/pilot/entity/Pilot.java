/**
 * 
 */
package com.prodyna.pac.flightplan.pilot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.flightplan.plane.entity.AircraftType;
import com.prodyna.pac.flightplan.user.entity.User;

/**
 * Entity object representing a pilot.
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "pilot")
@PrimaryKeyJoinColumn(name = "id")
@NamedQueries({ @NamedQuery(name = Pilot.QUERY_LOAD_ALL_PILOTS, query = "FROM Pilot ORDER BY userName") })
public class Pilot extends User implements Serializable {

    private static final long serialVersionUID = -5048278756046762394L;

    public static final String QUERY_LOAD_ALL_PILOTS = "load_all_pilots";

    public static final String PROP_LICENCE_VALIDITY = "licenceValidity";
    public static final String PROP_AIRCRAFTTYPES = "assignedAircraftTypes";

    @NotNull
    @Column(name = "licence_validity")
    private Date licenceValidity;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pilot_to_aircrafttype",
            joinColumns = { @JoinColumn(name = "pilot_id") },
            inverseJoinColumns = { @JoinColumn(name = "aircrafttype_id") })
    private List<AircraftType> assignedAircraftTypes;

    public Pilot() {
    }

    @XmlElement
    public Date getLicenceValidity() {
        return licenceValidity;
    }

    public void setLicenceValidity(Date licenceValidity) {
        this.licenceValidity = licenceValidity;
    }

    @XmlElement
    public List<AircraftType> getAssignedAircraftTypes() {
        return assignedAircraftTypes;
    }

    public void setAssignedAircraftTypes(List<AircraftType> assignedAircraftTypes) {
        this.assignedAircraftTypes = assignedAircraftTypes;
    }

    @Override
    public String toString() {
        return "Pilot [licenceValidity=" + licenceValidity + ", assignedAircraftTypes=" + assignedAircraftTypes
                + ", getId()=" + getId() + ", getUserName()=" + getUserName() + ", getFirstName()=" + getFirstName()
                + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword() + ", geteMailAddress()="
                + geteMailAddress() + "]";
    }
}
