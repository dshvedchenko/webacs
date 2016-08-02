package org.shved.webacs.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author dshvedchenko on 6/8/16.
 */
@Data
//permission
@Entity
@Table(name = "permission", schema = "app")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "permission")
    @Getter
    private List<PermissionClaim> permissionClaims;

    @ManyToMany(mappedBy = "permissions")
    private Set<AppUser> appUsers;

    public void update(Permission updatedPermission) {
        setTitle(updatedPermission.getTitle());
        setDescription(updatedPermission.getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != getClass()) return false;
        Permission inputObj = (Permission) obj;

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
