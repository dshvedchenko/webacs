package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IPermissionDAO;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.model.Permission;
import org.shved.webacs.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    IPermissionDAO permissionDAO;
    @Autowired
    IAppUserDAO appUserDAO;
    @Autowired
    IResourceDAO resourceDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PermissionDTO create(PermissionDTO permissionDTO) {
        return null;
    }

    @Override
    public PermissionDTO getById(Long id) {
        return null;
    }

    @Override
    public void update(PermissionDTO permissionDTO) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<PermissionDTO> getAll() {
        List<Permission> permissions = permissionDAO.findAllPermissions();
        return permissions.stream().map(permission -> modelMapper.map(permission, PermissionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> getAllByResourceId(Long resourceId) {
        return null;
    }
}
