package org.shved.webacs.dao;

import org.shved.webacs.model.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/13/16.
 */
public interface IPermissionClaimDAO {
    List<PermissionClaim> findAllPermissionClaim();

    List<PermissionClaim> findAllByClaimState(ClaimState claimState);

    List<PermissionClaim> findAllByResource(Resource resource);

    List<PermissionClaim> findAllByPermission(Permission permission);

    List<PermissionClaim> findAllByPermissionByUserNotRevoked(Permission permission, AppUser appUser);

    List<PermissionClaim> findAllByUser(AppUser appUser);
    List<PermissionClaim> findAllByUserNotRevoked(AppUser appUser);

    List<PermissionClaim> findAllClaimed();

    List<PermissionClaim> findAllApproved();

    List<PermissionClaim> findAllGranted();

    List<PermissionClaim> findAllRevoked();

    List<PermissionClaim> findAllToBeApprovedBy(AppUser appUser);

    PermissionClaim findById(Long id);

    void deleteById(Long id);
    void save(PermissionClaim permissionClaim);
}
