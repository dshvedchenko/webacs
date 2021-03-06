package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
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
import org.springframework.transaction.annotation.Propagation;
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
    private ModelMapper modelMapper;

    @Autowired
    private IContextUserService contextUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> getAll() {
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaim();
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> getAllOwn() {
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllByUser(contextUserService.getContextUser());
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> getAllByState(ClaimState claimState) {
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByClaimState(claimState));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaim> getAllClaimedForApproval() {
        AppUser appUser = contextUserService.getContextUser();
        return permissionClaimDAO.findAllToBeApprovedBy(appUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PermissionClaimDTO getById(Long id) {
        PermissionClaim claim = permissionClaimDAO.findById(id);
        AppUser appUser = contextUserService.getContextUser();

        if (appUser.equals(claim.getUser()) || contextUserService.isAdmin()) {
            return modelMapper.map(claim, PermissionClaimDTO.class);
        } else {
            throw new AppException("user id mismatch");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> getAllByResource(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByResource(resource));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> getAllByPermission(PermissionDTO permissionDTO) {
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimDAO.findAllByPermission(permission));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PermissionClaimDTO> create(List<CreatePermissionClaimDTO> createClaims) {

        List<Permission> newClaimedPermissionList = createClaims.stream()
                .map(item -> {
                    Permission permission = new Permission();
                    permission.setId(item.getPermissionId());
                    return permission;
                })
                .collect(Collectors.toList());

        AppUser appUser = contextUserService.getContextUser();
        List<PermissionClaim> activeUserClaims = permissionClaimDAO.findAllByUser(appUser);
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim currentPermissionClaim = permissionClaimDAO.findById(id);
        Boolean ownerOfClaim = contextUser == currentPermissionClaim.getUser();
        Boolean deleteAllowedInState = currentPermissionClaim.getClaimState() == ClaimState.CLAIMED;
        if ((ownerOfClaim || contextUserService.isAdmin()) && deleteAllowedInState) {
            permissionClaimDAO.deleteById(id);
        } else {
            throw new AppException("DELETE REJECTED in state " + currentPermissionClaim.getClaimState());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void approve(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (!permissionClaim.getClaimState().equals(ClaimState.CLAIMED))
            throw new AppException("Wrong Claim state for Approval");

        boolean isOwner = resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), contextUser);
        if (isOwner) {
            permissionClaim.setApprovedAt(new Date());
            permissionClaim.setClaimState(ClaimState.APPROVED);
            permissionClaim.setApprover(contextUser);
            permissionClaimDAO.save(permissionClaim);
        } else {
            throw new AppException("Can not approve");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decline(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (!permissionClaim.getClaimState().equals(ClaimState.CLAIMED))
            throw new AppException("Wrong Claim state for Approval");

        boolean isOwner = resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), contextUser);
        if (isOwner) {
            permissionClaim.setClaimState(ClaimState.DECLINED);
            permissionClaim.setApprover(contextUser);
            permissionClaimDAO.save(permissionClaim);
        } else {
            throw new AppException("Can not approve");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void grant(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);
        if (!permissionClaim.getClaimState().equals(ClaimState.APPROVED))
            throw new AppException("Wrong Claim state for GRANTING");

        permissionClaim.setGrantedAt(new Date());
        permissionClaim.setClaimState(ClaimState.GRANTED);
        permissionClaim.setGranter(contextUser);
        UserPermission userPermission = new UserPermission();
        userPermission.setClaim(permissionClaim);
        userPermissionDAO.save(userPermission);
        permissionClaimDAO.save(permissionClaim);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void revoke(Long claimId) {
        AppUser contextUser = contextUserService.getContextUser();
        PermissionClaim permissionClaim = permissionClaimDAO.findById(claimId);

        if (!permissionClaim.getClaimState().equals(ClaimState.GRANTED))
            throw new AppException("Wrong Claim staet for REVOKING");

        if (contextUser.getSysrole() == SysRole.ADMIN || resourceService.isOwnerOfResource(permissionClaim.getPermission().getResource(), contextUser)) {
            permissionClaim.setRevokedAt(new Date());
            permissionClaim.setClaimState(ClaimState.REVOKED);
            permissionClaim.setRevoker(contextUser);
            permissionClaimDAO.save(permissionClaim);
            userPermissionDAO.deleteByClaim(permissionClaim);
        }

    }

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        List<PermissionClaimDTO> permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
        return permissions;
    }

    private boolean isAlreadyClaimed(List<Permission> newClaimedPermissionList, List<PermissionClaim> activeUserClaims) {
        List<Permission> aliveUserPermissions = activeUserClaims.stream().map(item -> item.getPermission()).collect(Collectors.toList());
        aliveUserPermissions.retainAll(newClaimedPermissionList);
        return aliveUserPermissions.size() > 0;
    }
}
