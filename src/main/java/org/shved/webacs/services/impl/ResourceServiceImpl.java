package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.model.Resource;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    IResourceDAO resourceDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional()
    public ResourceDTO create(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        resourceDAO.saveResource(resource);
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
    public void updateResource(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        Resource toSave = resourceDAO.findById(resource.getId());
        toSave.setDetail(resource.getDetail());
        toSave.setKind(resource.getKind());
        //TODO resolve trouble with storing such entities from REST
        //toSave.setOwnerPermission();
        resourceDAO.saveResource(toSave);
    }

    @Override
    public void deleteById(Long id) {
        resourceDAO.deleteById(id);
    }

    @Override
    public List<ResourceDTO> getAll() {
        return convertListResourcesToListResourceDTO(resourceDAO.findAllResources());
    }

    private List<ResourceDTO> convertListResourcesToListResourceDTO(List<Resource> resources) {
        return resources.stream().map(item -> modelMapper.map(item, ResourceDTO.class)).collect(Collectors.toList());
    }

}
