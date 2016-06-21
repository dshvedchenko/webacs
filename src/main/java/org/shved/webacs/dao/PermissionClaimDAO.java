package org.shved.webacs.dao;

import org.shved.webacs.model.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public interface PermissionClaimDAO {
    List<PermissionClaim> findAllPermissionClaim();

    void savePermissionClaim(PermissionClaim permissionClaim);

    List<PermissionClaim> findAllByClaimState(ClaimState claimState);

    List<PermissionClaim> findAllByResource(Resource resource);

    List<PermissionClaim> findAllByPermission(Permission permission);

    List<PermissionClaim> findAllClaimed();

    List<PermissionClaim> findAllApproved();

    List<PermissionClaim> findAllGranted();

    List<PermissionClaim> findAllRevoked();

    List<PermissionClaim> findAllToBeApprovedBy(AppUser appUser);
}
