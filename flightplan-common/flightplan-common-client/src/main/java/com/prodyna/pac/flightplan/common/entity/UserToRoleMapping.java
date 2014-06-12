/**
 * 
 */
package com.prodyna.pac.flightplan.common.entity;

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
@Table(name = "user_to_role_mapping", schema = "flightplan")
public class UserToRoleMapping {

    @Id
    private String id;

    @ManyToOne
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
