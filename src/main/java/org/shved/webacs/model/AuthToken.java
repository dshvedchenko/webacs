package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Entity
@Data
@Table(name = "authtoken", schema = "app")
public class AuthToken {

    @Id
    @Column(name = "token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used")
    private Date lastUsed;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        AuthToken inputObj = (AuthToken) obj;

        if (this.getToken() != inputObj.getToken()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getToken().hashCode();

        return result;
    }
}
