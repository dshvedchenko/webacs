package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
@Entity
@Table(name = "appuser", schema = "app")
//appuser
public class AppUser {
    @OneToMany(mappedBy = "user")
    List<UserPermission> permissions;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "sysrole")
    private Integer sysrole;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        AppUser inputObj = (AppUser) obj;

        if (this.getId() != inputObj.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId().hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb
                .append("ID : " + id + "\r\n")
                .append("USERNAME : " + username + "\r\n")
                .append("EMAIL : " + email + "\r\n");
        return sb.toString();
    }
}
