package com.prodyna.stream.backend.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

/**
 * The entity for a Channel.
 */
@Entity("channels")
public class Channel implements Serializable {

    @Id
    private long id;

    private String key;
    private String label;
    private boolean readOnly;
    private boolean forceAssignment;

    public Channel() {
        super();
    }

    public Channel(long id, String key, String label, boolean readOnly, boolean forceAssignment) {
        this.id = id;
        this.key = key;
        this.label = label;
        this.readOnly = readOnly;
        this.forceAssignment = forceAssignment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isForceAssignment() {
        return forceAssignment;
    }

    public void setForceAssignment(boolean forceAssignment) {
        this.forceAssignment = forceAssignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel that = (Channel) o;

        if (forceAssignment != that.forceAssignment) return false;
        if (id != that.id) return false;
        if (readOnly != that.readOnly) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (readOnly ? 1 : 0);
        result = 31 * result + (forceAssignment ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", label='" + label + '\'' +
                ", readOnly=" + readOnly +
                ", forceAssignment=" + forceAssignment +
                '}';
    }
}
