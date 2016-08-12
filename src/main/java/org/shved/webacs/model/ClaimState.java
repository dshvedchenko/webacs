package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Entity
@Table(name = "claim_state", schema = "app")
@Data
public class ClaimState {

    public static ClaimState CLAIMED;
    public static ClaimState APPROVED;
    public static ClaimState GRANTED;
    public static ClaimState REVOKED;
    public static ClaimState DECLINED;

    static {
        CLAIMED = new ClaimState();
        CLAIMED.setId(0);
        APPROVED = new ClaimState();
        APPROVED.setId(1);
        GRANTED = new ClaimState();
        GRANTED.setId(2);
        REVOKED = new ClaimState();
        REVOKED.setId(3);
        DECLINED = new ClaimState();
        DECLINED.setId(4);
    }

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

        return this.getId() == inputObj.getId();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId().hashCode();

        return result;
    }
}
