package org.shved.webacs.services.impl;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.dto.*;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.services.IPermissionClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 7/5/16.
 */
@Service
@Data
public class PermissionClaimServiceImpl implements IPermissionClaimService {

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

    // TODO,
    //
    // 2. verify claim already exists for permission for time period ( look only in not revoked , expired claims )
    // 3. how to check permissions
    // 4. provide to user only list of permission that is not claimed to him already
    @Override
    @Transactional
    public List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> createClaims, AppUserDTO user) {
        List<Permission> newClaimedPermissionList = createClaims.stream().map(item -> modelMapper.map(item.getPermissionDTO(), Permission.class)).collect(Collectors.toList());
        AppUser appUser = modelMapper.map(user, AppUser.class);
        List<PermissionClaim> activeUserClaims = permissionClaimDAO.findAllByUserNotRevoked(appUser);
        if (checkIsAlreadyClaimed(newClaimedPermissionList, activeUserClaims))
            throw new AppException("Permissions already claimed");

        List<PermissionClaim> newClaims = new LinkedList<>();
        for (Permission permission : newClaimedPermissionList) {
            PermissionClaim permissionClaim = new PermissionClaim();
            permissionClaim.setClaimState(ClaimState.CLAIMED);
            permissionClaim.setUser(appUser);
            permissionClaim.setPermission(permission);
            permissionClaim.setClaimedAt(new Date());
            newClaims.add(permissionClaim);
            permissionClaimDAO.save(permissionClaim);
        }

        return newClaims.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
    }

    private boolean checkIsAlreadyClaimed(List<Permission> newClaimedPermissionList, List<PermissionClaim> activeUserClaims) {
        List<Permission> aliveUserPermissions = activeUserClaims.stream().map(item -> item.getPermission()).collect(Collectors.toList());
        aliveUserPermissions.retainAll(newClaimedPermissionList);
        return aliveUserPermissions.size() > 0;
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
