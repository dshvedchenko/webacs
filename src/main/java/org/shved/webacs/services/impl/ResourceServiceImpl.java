package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.Resource;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
}
