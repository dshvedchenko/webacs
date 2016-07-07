package org.shved.webacs.services.impl;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.dao.impl.PermissionClaimDAOImpl;
import org.shved.webacs.dto.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.services.IPermissionClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 7/5/16.
 */
@Service
@Data
public class PermissionClaimService implements IPermissionClaimService {

    @Autowired
    IPermissionClaimDAO permissionClaimDAO;

    @Autowired
    IUserPermissionDAO userPermissionDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PermissionClaimDTO> getAll() {
        //only admin can get list of all claims
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaim();
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
    }

    @Override
    public List<PermissionClaimDTO> getAllByState(ClaimStateDTO claimStateDTO) {
        // only admin can see by claimstates
        return null;
    }

    @Override
    public PermissionClaimDTO getById(Long id) {
        PermissionClaim claim = permissionClaimDAO.findById(id);
        return modelMapper.map(claim, PermissionClaimDTO.class);
    }

    @Override
    public List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO) {
        return null;
    }

    @Override
    public List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO) {
        return null;
    }

    // TODO, 1. how to pass user ( by token or userid )
    // 2. verify claim already exists for permission for time period ( look only in not revoked , expired claims )
    // 3. how to check permissions
    @Override
    public PermissionClaimDTO create(PermissionDTO permissionDTO, AppUserDTO user) {
        Permission claimedPermission = modelMapper.map(permissionDTO, Permission.class);
        AppUser appUser = modelMapper.map(user, AppUser.class);
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllByPermissionByUserNotRevoked(claimedPermission, appUser);

        return null;
    }

    @Override
    public void update(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {
        //only creator can update claim
    }

    @Override
    public void delete(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {
        // no one can delete claim ( yet )
    }

    @Override
    public void approve(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {
        //only owner can approve claim
    }

    @Override
    public void grant(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {
        //only admin can approve claim
    }

    @Override
    public void revoke(PermissionClaimDTO permissionClaimDTO, AppUserDTO user) {
        //only admin,owner can revoke claim
    }

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        List<PermissionClaimDTO> permissions = null;
        permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
        return permissions;
    }
}
