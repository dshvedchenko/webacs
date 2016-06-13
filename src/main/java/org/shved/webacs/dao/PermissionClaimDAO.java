package org.shved.webacs.dao;

import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public interface PermissionClaimDAO {
    List<PermissionClaim> findAllPermissionClaim();

    void savePermissionClaim(PermissionClaim permissionClaim);
    List<PermissionClaim> findAllPermissionClaimByClaimState(ClaimState claimState);
}
