package org.shved.webacs.dao;

import org.shved.webacs.model.Resource;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public interface IResourceDAO {

    List<Resource> findAllResources();

    Resource findById(Long id);

    void saveResource(Resource resource);

    void delete(Resource resource);

    void deleteById(Long id);

    List<Resource> findAllByKind(String kind);

    Resource findByName(String name);

}
