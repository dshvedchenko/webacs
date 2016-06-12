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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kind")
    private String kind;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @OneToOne(fetch = FetchType.LAZY)
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
        result = result * prime + getId().hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESOURCE : ").append(getName()).append(" [").append(getId()).append("]");
        return sb.toString();
    }

}
