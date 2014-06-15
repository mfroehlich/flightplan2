/**
 * 
 */
package com.prodyna.pac.flightplan.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Entity
@Table(name = "role", schema = "flightplan")
public class Role implements Serializable {

    private static final long serialVersionUID = -7987759744738655813L;

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String GUEST = "GUEST";

    @Id
    private String id;

    @Column(name = "rolename")
    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
