package org.shved.webacs.services;

import org.shved.webacs.dto.ResourceCreationDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/26/16.
 */
public interface IResourceService {

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    ResourceDTO create(ResourceCreationDTO resourceDTO);

    ResourceDTO getById(Long id);

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateResource(ResourceDTO resourceDTO);

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(Long id);

    @Transactional
    List<ResourceDTO> getAll();

    @Transactional
    List<ResourceDTO> getAllByResTypeId(Integer typeId);

    @Transactional
    boolean isOwnerOfResource(Resource resource, AppUser appUser);

}
