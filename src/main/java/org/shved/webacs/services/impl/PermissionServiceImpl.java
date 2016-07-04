package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IPermissionDAO;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.model.Permission;
import org.shved.webacs.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public PermissionDTO create(PermissionDTO permissionDTO) {
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        permissionDAO.save(permission);
        Permission newPerm = permissionDAO.findById(permission.getId());
        PermissionDTO result = modelMapper.map(newPerm, PermissionDTO.class);
        return result;
    }

    @Override
    public PermissionDTO getById(Long id) {
        Permission permission = permissionDAO.findById(id);
        if (permission == null) throw new NotFoundException();
        return modelMapper.map(permission, PermissionDTO.class);
    }

    @Override
    public void update(PermissionDTO permissionDTO) {
        Permission updatedPermission = modelMapper.map(permissionDTO, Permission.class);
        Permission existingPermission = permissionDAO.findById(permissionDTO.getId());
        if (existingPermission != null) {
            existingPermission.update(updatedPermission);
            permissionDAO.save(existingPermission);
        } else {
            throw new NotFoundException("Permission not found : " + updatedPermission.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        permissionDAO.deleteById(id);
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
