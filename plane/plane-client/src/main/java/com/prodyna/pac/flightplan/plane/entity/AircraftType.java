/**
 * 
 */
package com.prodyna.pac.flightplan.plane.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity object representing an aircraft type.
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "aircrafttype")
@NamedQueries({
        @NamedQuery(name = AircraftType.QUERY_LOAD_ALL_TYPES, query = "FROM AircraftType ORDER BY description"),
        @NamedQuery(
                name = AircraftType.QUERY_COUNT_OTHER_AIRCRAFTTYPES_DEFINING_DESCRIPTION,
                query = "SELECT COUNT(*) FROM AircraftType WHERE id <> :aircraftTypeId AND description = :description") })
public class AircraftType implements Serializable {

    private static final long serialVersionUID = -6024676696405518559L;

    public static final String QUERY_LOAD_ALL_TYPES = "load_all_types";

    public static final String PROP_ID = "id";
    public static final String PROP_DESCRIPTION = "description";
    public static final String QUERY_COUNT_OTHER_AIRCRAFTTYPES_DEFINING_DESCRIPTION = "query_count_other_aircrafttypes_defining_description";

    public AircraftType() {
    }

    @NotNull
    @Size(min = 1, max = 50)
    @Id
    private String id;

    @Version
    private int version;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description", unique = true)
    private String description;

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        AircraftType other = (AircraftType) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AircraftType [id=" + id + ", description=" + description + "]";
    }
}
