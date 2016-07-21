package org.shved.webacs.services;

import org.shved.webacs.dto.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.PermissionClaim;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author dshvedchenko on 7/5/16.
 */
public interface IPermissionClaimService {

    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAll();

    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByState(ClaimStateDTO claimStateDTO);

    PermissionClaimDTO getById(Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO);

    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO);

    List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> permissionClaimDTOList);

    void update(PermissionClaimDTO permissionClaimDTO);

    void delete(Long id);

    void approve(Long claimId);

    @PreAuthorize("hasAuthority('ADMIN')")
    void grant(Long claimId);

    void revoke(Long claimId);

}
