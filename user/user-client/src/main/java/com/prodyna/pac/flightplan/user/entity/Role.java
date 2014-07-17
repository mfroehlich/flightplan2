/**
 * 
 */
package com.prodyna.pac.flightplan.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = -7987759744738655813L;

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @NotNull
    @Size(min = 1, max = 50)
    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "role_name", unique = true)
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
