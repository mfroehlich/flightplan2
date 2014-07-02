/**
 * 
 */
package com.prodyna.pac.flightplan.user.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 * 
 */
@Entity
@Table(name = "user_to_role_mapping")
public class UserToRoleMapping implements Serializable {

    private static final long serialVersionUID = -1431125262011670011L;

    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @ManyToOne
    private Role role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
