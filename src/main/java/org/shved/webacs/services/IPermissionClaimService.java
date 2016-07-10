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

    List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> permissionClaimDTOList, String username);

    void update(PermissionClaimDTO permissionClaimDTO, String username);

    void delete(PermissionClaimDTO permissionClaimDTO, String username);

    void approve(PermissionClaimDTO permissionClaimDTO, String username);

    void grant(PermissionClaimDTO permissionClaimDTO, String username);

    void revoke(PermissionClaimDTO permissionClaimDTO, String username);
}
