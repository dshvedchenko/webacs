package org.shved.webacs.services.impl;

import lombok.Data;
import org.shved.webacs.dto.*;
import org.shved.webacs.services.IPermissionClaimService;

import java.util.List;

/**
 * @author dshvedchenko on 7/5/16.
 */
@Data
public class PermissionClaimService implements IPermissionClaimService {
    @Override
    public List<PermissionClaimDTO> getAll() {
        return null;
    }

    @Override
    public List<PermissionClaimDTO> getAllByState(ClaimStateDTO claimStateDTO) {
        return null;
    }

    @Override
    public PermissionClaimDTO getById(Long id) {
        return null;
    }

    @Override
    public List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO) {
        return null;
    }

    @Override
    public List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO) {
        return null;
    }

    @Override
    public PermissionClaimDTO create(PermissionClaimDTO permissionClaimDTO) {
        return null;
    }

    @Override
    public void update(PermissionClaimDTO permissionClaimDTO) {

    }

    @Override
    public void delete(PermissionClaimDTO permissionClaimDTO) {

    }

    @Override
    public void approve(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {

    }

    @Override
    public void grant(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {

    }

    @Override
    public void revoke(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {

    }
}
