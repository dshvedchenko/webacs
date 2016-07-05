package org.shved.webacs.services.impl;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IPermissionClaimDAO;
import org.shved.webacs.dao.IUserPermissionDAO;
import org.shved.webacs.dao.impl.PermissionClaimDAOImpl;
import org.shved.webacs.dto.*;
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
        List<PermissionClaim> permissionClaimList = permissionClaimDAO.findAllPermissionClaim();
        return convertListPermissionClaimsToPermissionClaimDTO(permissionClaimList);
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

    private List<PermissionClaimDTO> convertListPermissionClaimsToPermissionClaimDTO(List<PermissionClaim> permissionClaimList) {
        List<PermissionClaimDTO> permissions = null;
        permissions = permissionClaimList.stream().map(item -> modelMapper.map(item, PermissionClaimDTO.class)).collect(Collectors.toList());
        return permissions;
    }
}
