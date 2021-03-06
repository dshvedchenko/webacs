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

    public void update(ResType updatedItem) {
        setName(updatedItem.getName());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        ResType inputObj = (ResType) obj;

        return this.getId().equals(inputObj.getId());

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
        return getName();
    }


}
