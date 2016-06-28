package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dshvedchenko on 6/28/16.
 */
@Data
//resource
@Entity
@Table(name = "restype", schema = "app")
public class ResType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        ResType inputObj = (ResType) obj;

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
