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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at")
    private Date startAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at")
    private Date endAt;

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

        if (this.getStartAt() == null && inputObj.getStartAt() != null) return false;
        if (inputObj.getStartAt() == null && this.getStartAt() != null) return false;
        if (this.getStartAt() != inputObj.getStartAt()) return false;

        if (this.getEndAt() == null && inputObj.getEndAt() != null) return false;
        if (inputObj.getEndAt() == null && this.getEndAt() != null) return false;
        if (this.getEndAt() != inputObj.getEndAt()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId().hashCode();
        result = result * prime + getUser().hashCode();
        result = result * prime + getPermission().hashCode();

        return result;
    }


}
