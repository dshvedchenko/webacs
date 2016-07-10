package org.shved.webacs.services;

import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Resource;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
public interface IResourceService {

    ResourceDTO create(ResourceDTO resourceDTO);

    ResourceDTO getById(Long id);

    void updateResource(ResourceDTO resourceDTO);

    void deleteById(Long id);

    List<ResourceDTO> getAll();

    List<ResourceDTO> getAllByResTypeId(Integer typeId);

    boolean isOwnerOfResource(Resource resource, AppUser appUser);

}
