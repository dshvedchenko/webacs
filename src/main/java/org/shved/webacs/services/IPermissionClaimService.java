package org.shved.webacs.services;

import org.shved.webacs.dto.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.PermissionClaim;

import java.util.List;

/**
 * @author dshvedchenko on 7/5/16.
 */
public interface IPermissionClaimService {
    List<PermissionClaimDTO> getAll();

    List<PermissionClaimDTO> getAllByState(ClaimStateDTO claimStateDTO);

    PermissionClaimDTO getById(Long id);

    List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO);

    List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO);

    PermissionClaimDTO create(PermissionDTO permissionClaimDTO, AppUserDTO user);

    void update(PermissionClaimDTO permissionClaimDTO, AppUserDTO user);

    void delete(PermissionClaimDTO permissionClaimDTO, AppUserDTO user);

    void approve(PermissionClaimDTO permissionClaimDTO, AppUserDTO user);

    void grant(PermissionClaimDTO permissionClaimDTO, AppUserDTO user);

    void revoke(PermissionClaimDTO permissionClaimDTO, AppUserDTO user);
}
