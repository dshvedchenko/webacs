package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Entity
@Table(name = "claim_state", schema = "app")
@Data
public class ClaimState {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        ClaimState inputObj = (ClaimState) obj;

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
}
