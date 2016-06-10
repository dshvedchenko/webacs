package org.shved.webacs.model;

import com.sun.istack.internal.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
//user_permission
@Entity
@Table(name = "user_permission", schema = "app")
public class UserPermission {

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
    @Column(name = "start_at")
    private Date startAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at")
    private Date endAt;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne
    @JoinColumn(name = "claim_id", referencedColumnName = "id")
    private PermissionClaim claim;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        UserPermission inputObj = (UserPermission) obj;

        if (this.getId() != inputObj.getId()) return false;

        if (this.getUser() != inputObj.getUser()) return false;

        if (this.getPermission() != inputObj.getPermission()) return false;

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
        result = result * prime + getId();

        return result;
    }
}
