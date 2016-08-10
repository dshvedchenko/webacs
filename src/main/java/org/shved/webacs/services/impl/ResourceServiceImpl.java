package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IPermissionDAO;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.dto.ResourceCreationDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.Resource;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private IResourceDAO resourceDAO;

    @Autowired
    private IPermissionDAO permissionDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ResourceDTO create(ResourceCreationDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        resourceDAO.save(resource);
        return modelMapper.map(resource, ResourceDTO.class);
    }

    @Override
    @Transactional
    public ResourceDTO getById(Long id) {
        Resource resource = resourceDAO.findById(id);
        if (resource == null) throw new NotFoundException("not found for " + id);

        return modelMapper.map(resource, ResourceDTO.class);
    }

    @Override
    @Transactional
    public void updateResource(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        Resource toSave = resourceDAO.findById(resource.getId());

        setNewOwnerPermission(resourceDTO, toSave);

        toSave.setDetail(resource.getDetail());
        toSave.setResType(resource.getResType());
        resourceDAO.save(toSave);
    }

    private void setNewOwnerPermission(ResourceDTO resourceDTO, Resource toSave) {
        Long newOwnerPermissionId = resourceDTO.getOwnerPermission() == null ? null : resourceDTO.getOwnerPermission().getId();
        Permission newOwnerPermission = null;
        if (newOwnerPermissionId != null) {
            newOwnerPermission = permissionDAO.findById(newOwnerPermissionId);
            toSave.updateOwnerPermission(newOwnerPermission);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            resourceDAO.deleteById(id);
        } catch (Exception e) {
            throw new AppException("could not delete resource ", e);
        }
    }

    @Override
    @Transactional
    public List<ResourceDTO> getAll() {
        return convertListResourcesToListResourceDTO(resourceDAO.findAllResources());
    }

    @Override
    @Transactional
    public List<ResourceDTO> getAllByResTypeId(Integer typeId) {
        return convertListResourcesToListResourceDTO(resourceDAO.findAllByResTypeId(typeId));
    }

    @Override
    @Transactional
    public boolean isOwnerOfResource(Resource resource, AppUser appUser) {
        return resource.getOwnerPermission().getAppUsers().contains(appUser);
    }

    private List<ResourceDTO> convertListResourcesToListResourceDTO(List<Resource> resources) {
        return resources.stream().map(item ->
                modelMapper.map(item, ResourceDTO.class)
        ).collect(Collectors.toList());
    }

}
