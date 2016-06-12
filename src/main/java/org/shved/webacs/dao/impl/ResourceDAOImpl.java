package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.ResourceDAO;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class ResourceDAOImpl extends AbstractDao<Long, Resource> implements ResourceDAO {

    @Autowired
    SessionFactory sessionFactory;

    public ResourceDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Resource> findAllResources() {
        List<Resource> resources = getSession().createCriteria(Resource.class).list();
        return resources;
    }

    @Override
    public Resource findById(Long id) {
        return null;
    }

    @Override
    public void saveResource(Resource resource) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Resource> findAllByKind(String kind) {
        return null;
    }

    @Override
    public Resource findByName(String name) {
        return null;
    }
}
