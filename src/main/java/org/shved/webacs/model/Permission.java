package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
//permission
@Entity
@Table(name = "permission", schema = "app")
public class Permission {
    @OneToMany(mappedBy = "permission")
    Set<UserPermission> appUsers;
    //resource_id
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        Permission inputObj = (Permission) obj;

        if (this.getId() != inputObj.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId();

        return result;
    }


}
