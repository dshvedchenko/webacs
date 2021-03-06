package org.shved.webacs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
//user_permission
@Entity
@Table(name = "user_permission", schema = "app")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;

    @OneToOne
    @JoinColumn(name = "claim_id", referencedColumnName = "id")
    private PermissionClaim claim;

    public void setClaim(PermissionClaim claim) {
        this.claim = claim;
        setUser(claim.getUser());
        setPermission(claim.getPermission());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        UserPermission inputObj = (UserPermission) obj;

        if (this.getId() != inputObj.getId()) return false;

        if (this.getUser() != inputObj.getUser()) return false;

        return this.getPermission() == inputObj.getPermission();


    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + getId().hashCode();

        return result;
    }
}
