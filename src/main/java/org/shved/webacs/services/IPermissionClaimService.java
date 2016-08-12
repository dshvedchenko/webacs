package org.shved.webacs.services;

import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.PermissionClaim;

import java.util.List;

/**
 * @author dshvedchenko on 7/5/16.
 */
public interface IPermissionClaimService {

    List<PermissionClaimDTO> getAll();
    List<PermissionClaimDTO> getAllOwn();
    List<PermissionClaimDTO> getAllByState(ClaimState claimState);
    PermissionClaimDTO getById(Long id);
    List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO);
    List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO);
    List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> permissionClaimDTOList);

    List<PermissionClaim> getAllClaimedForApproval();

    void update(PermissionClaimDTO permissionClaimDTO);
    void delete(Long id);
    void approve(Long claimId);
    void grant(Long claimId);
    void revoke(Long claimId);

    void decline(Long claimId);

}
