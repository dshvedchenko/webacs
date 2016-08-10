package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.model.*;
import org.shved.webacs.services.IContextUserService;
import org.shved.webacs.services.IPermissionClaimService;
import org.shved.webacs.services.IResourceService;
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
public class PermissionClaimServiceImpl implements IPermissionClaimService {

    @Autowired
    private IPermissionClaimDAO permissionClaimDAO;

    @Autowired
    private IUserPermissionDAO userPermissionDAO;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IAppUserDAO appUserDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IContextUserService contextUserService;

    @Override
    @Transactional
    public List<PermissionClaimDTO> getAll() {
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaim();
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
    }

    @Override
    @Transactional
    public List<PermissionClaimDTO> getAllOwn() {
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllByUserNotRevoked(contextUserService.getContextUser());
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
    }

    @Override
    @Transactional
    public List<PermissionClaimDTO> getAllByState(ClaimState claimState) {
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByClaimState(claimState));
    }

    @Override
    @Transactional
    public PermissionClaimDTO getById(Long id) {
        PermissionClaim claim = permissionClaimDAO.findById(id);
        AppUser appUser = contextUserService.getContextUser();

        if (appUser.equals(claim.getUser())) {
            return modelMapper.map(claim, PermissionClaimDTO.class);
        } else {
            throw new AppException("user id mismatch");
        }
    }

    @Override
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

    // 3. how to check permissions
    // 4. provide to user only list of permission that is not claimed to him already
    @Override
    @Transactional
    public List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> createClaims) {
        List<Permission> newClaimedPermissionList = createClaims.stream().map(item -> modelMapper.map(item.getPermissionDTO(), Permission.class)).collect(Collectors.toList());
        AppUser appUser = contextUserService.getContextUser();
        List<PermissionClaim> activeUserClaims = permissionClaimDAO.findAllByUserNotRevoked(appUser);
        if (isAlreadyClaimed(newClaimedPermissionList, activeUserClaims))
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
    @Transactional
    public void update(PermissionClaimDTO permissionClaimDTO) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = modelMapper.map(permissionClaimDTO, PermissionClaim.class);
        PermissionClaim currentPermissionClaim = permissionClaimDAO.findById(permissionClaim.getId());
        Boolean ownerOfClaim = contextUser == currentPermissionClaim.getUser();
        Boolean updateAllowedInState = currentPermissionClaim.getClaimState() == ClaimState.CLAIMED;
        if (ownerOfClaim && updateAllowedInState) {
            currentPermissionClaim.setEndAt(permissionClaim.getEndAt());
            currentPermissionClaim.setStartAt(permissionClaim.getStartAt());
            permissionClaimDAO.save(currentPermissionClaim);
        } else {
            throw new AppException("UPDATE REJECTED in state " + permissionClaim.getClaimState());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim currentPermissionClaim = permissionClaimDAO.findById(id);
        Boolean ownerOfClaim = contextUser == currentPermissionClaim.getUser();
        Boolean deleteAllowedInState = currentPermissionClaim.getClaimState() == ClaimState.CLAIMED;
        if (ownerOfClaim && deleteAllowedInState /* ADMINs also need ability to delete claims */) {
            permissionClaimDAO.deleteById(id);
        } else {
            throw new AppException("DELETE REJECTED in state " + currentPermissionClaim.getClaimState());
        }
    }

    @Override
    @Transactional
    public void approve(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (permissionClaim.getClaimState() != ClaimState.CLAIMED)
            throw new AppException("Wrong Claim state for Approval");

        if (resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), contextUser)) {
            permissionClaim.setApprovedAt(new Date());
            permissionClaim.setClaimState(ClaimState.APPROVED);
            permissionClaim.setApprover(contextUser);
            UserPermission userPermission = new UserPermission();
            userPermission.setClaim(permissionClaim);
            userPermissionDAO.save(userPermission);
            permissionClaimDAO.save(permissionClaim);
        } else {
            throw new AppException("Can not approve");
        }

    }

    @Override
    @Transactional
    public void grant(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);
        if (permissionClaim.getClaimState() != ClaimState.APPROVED)
            throw new AppException("Wrong Claim staet for GRANTING");

            permissionClaim.setGrantedAt(new Date());
            permissionClaim.setClaimState(ClaimState.GRANTED);
        permissionClaim.setGranter(contextUser);
            userPermissionDAO.deleteByClaim(permissionClaim);
            permissionClaimDAO.save(permissionClaim);
    }

    @Override
    @Transactional
    public void revoke(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (permissionClaim.getClaimState() != ClaimState.GRANTED)
            throw new AppException("Wrong Claim staet for REVOKING");

        if (contextUser.getSysrole() == SysRole.ADMIN || resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), contextUser)) {
            permissionClaim.setRevokedAt(new Date());
            permissionClaim.setClaimState(ClaimState.REVOKED);
            permissionClaim.setRevoker(contextUser);
            userPermissionDAO.deleteByClaim(permissionClaim);
            permissionClaimDAO.save(permissionClaim);
        }

    }

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        List<PermissionClaimDTO> permissions = null;
        permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
        return permissions;
    }

    private boolean isAlreadyClaimed(List<Permission> newClaimedPermissionList, List<PermissionClaim> activeUserClaims) {
        List<Permission> aliveUserPermissions = activeUserClaims.stream().map(item -> item.getPermission()).collect(Collectors.toList());
        aliveUserPermissions.retainAll(newClaimedPermissionList);
        return aliveUserPermissions.size() > 0;
    }
}
