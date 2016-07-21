package org.shved.webacs.services;

import org.shved.webacs.dto.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.PermissionClaim;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 7/5/16.
 */
public interface IPermissionClaimService {

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAll();

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByState(ClaimStateDTO claimStateDTO);


    @Transactional
    PermissionClaimDTO getById(Long id);

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO);

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO);

    @Transactional
    List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> permissionClaimDTOList);

    @Transactional
    void update(PermissionClaimDTO permissionClaimDTO);

    @Transactional
    void delete(Long id);

    @Transactional
    void approve(Long claimId);

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    void grant(Long claimId);

    @Transactional
    void revoke(Long claimId);

}
