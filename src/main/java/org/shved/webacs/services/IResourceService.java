package org.shved.webacs.services;

import org.shved.webacs.dto.ResourceDTO;

/**
 * @author dshvedchenko on 6/26/16.
 */
public interface IResourceService {

    ResourceDTO create(ResourceDTO resourceDTO);
}
