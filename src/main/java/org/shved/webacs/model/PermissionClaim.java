package org.shved.webacs.model;

import lombok.Data;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
@Entity
@Table(name = "permission_claim", schema = "app")
public class PermissionClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "claimed_at")
    private Date claimedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approver_id", referencedColumnName = "id")
    private AppUser approver;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approved_at")
    private Date approvedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "granter_id", referencedColumnName = "id")
    private AppUser granter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "granted_at")
    private Date grantedAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        PermissionClaim inputObj = (PermissionClaim) obj;

        if (this.getId() != inputObj.getId()) return false;

        if (this.getUser() != inputObj.getUser()) return false;

        if (this.getPermission() != inputObj.getPermission()) return false;

        if (this.getClaimedAt() != inputObj.getClaimedAt()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId();
        result = result * prime + getUser().hashCode();
        result = result * prime + getPermission().hashCode();

        return result;
    }


}
