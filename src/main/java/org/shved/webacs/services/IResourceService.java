package org.shved.webacs.services;

import org.shved.webacs.dto.ResourceCreationDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
public interface IResourceService {


    @PreAuthorize("hasAuthority('ADMIN')")
    ResourceDTO create(ResourceCreationDTO resourceDTO);

    ResourceDTO getById(Long id);


    @PreAuthorize("hasAuthority('ADMIN')")
    void updateResource(ResourceDTO resourceDTO);


    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(Long id);


    List<ResourceDTO> getAll();


    List<ResourceDTO> getAllByResTypeId(Integer typeId);


    boolean isOwnerOfResource(Resource resource, AppUser appUser);

}
