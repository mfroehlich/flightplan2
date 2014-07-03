package com.prodyna.pac.flightplan.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity object representing a user.
 * 
 * @author mfroehlich
 * 
 */
@XmlRootElement
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(
                name = User.QUERY_UPDATE_USER_PASSWORD,
                query = "UPDATE User SET password = :newPwd, version = (version + 1) WHERE id = :userId AND password = :oldPwd "),
        @NamedQuery(
                name = User.QUERY_LOAD_USER_ID_BY_USERNAME,
                query = "SELECT id FROM User WHERE userName = :username"),
        @NamedQuery(
                name = User.QUERY_COUNT_OTHER_USERS_WITH_USERNAME,
                query = "SELECT COUNT(id) FROM User WHERE userName = :userName AND id <> :userId ") })
public class User implements Serializable {

    private static final long serialVersionUID = 3513803091974740763L;

    public static final String QUERY_UPDATE_USER_PASSWORD = "update_user_password";
    public static final String QUERY_LOAD_USER_ID_BY_USERNAME = "load_user_id_by_username";
    public static final String QUERY_COUNT_OTHER_USERS_WITH_USERNAME = "query_count_other_users_with_username";

    public static final String PROP_ID = "id";
    public static final String PROP_USERNAME = "userName";
    public static final String PROP_FIRSTNAME = "firstName";
    public static final String PROP_LASTNAME = "lastName";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_EMAIL = "eMailAddress";

    @NotNull
    @Size(min = 1, max = 50)
    @Id
    private String id;

    @Version
    private int version;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username", unique = true)
    private String userName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
    @Column(name = "email")
    private String eMailAddress;

    public User() {
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public String geteMailAddress() {
        return eMailAddress;
    }

    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
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
        result = prime * result + ((eMailAddress == null) ? 0 : eMailAddress.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        User other = (User) obj;
        if (eMailAddress == null) {
            if (other.eMailAddress != null)
                return false;
        } else if (!eMailAddress.equals(other.eMailAddress))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
                + ", password=" + password + ", eMailAddress=" + eMailAddress + "]";
    }
}
