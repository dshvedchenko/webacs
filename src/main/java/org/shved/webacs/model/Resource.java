package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
//resource
@Entity
@Table(name = "resource", schema = "app")
public class Resource {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "kind")
    private String kind;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @OneToOne
    @JoinColumn(name = "owner_permission_id")
    private Permission ownerPermission;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        Resource inputObj = (Resource) obj;

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
