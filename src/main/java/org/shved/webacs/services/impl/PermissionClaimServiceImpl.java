package org.shved.webacs.services.impl;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.dto.*;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.model.*;
import org.shved.webacs.services.IPermissionClaimService;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    IResourceService resourceService;

    @Autowired
    IAppUserDAO appUserDAO;

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
    @Secured("ADMIN")
    @Transactional
    public List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByResource(resource));
    }

    @Override
    public List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO) {
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByPermission(permission));
    }

    // TODO,
    // 3. how to check permissions
    // 4. provide to user only list of permission that is not claimed to him already
    @Override
    @Transactional
    public List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> createClaims, String username) {
        List<Permission> newClaimedPermissionList = createClaims.stream().map(item -> modelMapper.map(item.getPermissionDTO(), Permission.class)).collect(Collectors.toList());
        AppUser appUser = getAppUserFromUsername(username);
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

    @Override
    public void update(PermissionClaimDTO permissionClaimDTO, String username) {
        //only creator can update claim
    }

    @Override
    public void delete(PermissionClaimDTO permissionClaimDTO, String username) {
        // no one can delete claim ( yet )
    }

    @Override
    public void approve(Long claimId, String username) {
        AppUser executiveUser = getAppUserFromUsername(username);
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (permissionClaim.getClaimState() != ClaimState.CLAIMED)
            throw new AppException("Wrong Claim state for Approval");

        if (resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), executiveUser)) {
            permissionClaim.setApprovedAt(new Date());
            permissionClaim.setClaimState(ClaimState.APPROVED);
            permissionClaim.setApprover(executiveUser);
            UserPermission userPermission = new UserPermission();
            userPermission.setClaim(permissionClaim);
            userPermissionDAO.save(userPermission);
            permissionClaimDAO.save(permissionClaim);
        } else {
            throw new AppException("Can not approve");
        }

    }

    @Override
    @Secured("ADMIN")
    public void grant(Long claimId, String username) {
        AppUser executiveUser = getAppUserFromUsername(username);
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);
        if (permissionClaim.getClaimState() != ClaimState.APPROVED)
            throw new AppException("Wrong Claim staet for GRANTING");

        if (executiveUser.getSysrole() == SysRole.ADMIN) {
            permissionClaim.setGrantedAt(new Date());
            permissionClaim.setClaimState(ClaimState.GRANTED);
            permissionClaim.setGranter(executiveUser);
            userPermissionDAO.deleteByClaim(permissionClaim);
            permissionClaimDAO.save(permissionClaim);
        }
        ;
    }

    @Override
    @Transactional
    public void revoke(Long claimId, String username) {
        AppUser executiveUser = getAppUserFromUsername(username);
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (permissionClaim.getClaimState() != ClaimState.GRANTED)
            throw new AppException("Wrong Claim staet for REVOKING");

        if (executiveUser.getSysrole() == SysRole.ADMIN || resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), executiveUser)) {
            permissionClaim.setRevokedAt(new Date());
            permissionClaim.setClaimState(ClaimState.REVOKED);
            permissionClaim.setRevoker(executiveUser);
            userPermissionDAO.deleteByClaim(permissionClaim);
            permissionClaimDAO.save(permissionClaim);
        }
        ;

    }

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        List<PermissionClaimDTO> permissions = null;
        permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
        return permissions;
    }

    private boolean checkIsAlreadyClaimed(List<Permission> newClaimedPermissionList, List<PermissionClaim> activeUserClaims) {
        List<Permission> aliveUserPermissions = activeUserClaims.stream().map(item -> item.getPermission()).collect(Collectors.toList());
        aliveUserPermissions.retainAll(newClaimedPermissionList);
        return aliveUserPermissions.size() > 0;
    }

    private AppUser getAppUserFromUsername(String username) {
        return appUserDAO.findByUsername(username);
    }
}
