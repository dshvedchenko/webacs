package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.ResourceDAO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        List<Resource> resources = getSession().createCriteria(Resource.class)
                .setFetchMode("ownerPermission", FetchMode.JOIN)
                .list();
        return resources;
    }

    @Override
    public Resource findById(Long id) {
        return getByKey(id, Resource.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public void saveResource(Resource resource) {
        persist(resource);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public void deleteById(Long id) {

    }

    @Override
    public List<Resource> findAllByKind(String kind) {
        Criteria criteria = getSession().createCriteria(Resource.class)
                .add(Restrictions.ilike("kind", kind));
        List<Resource> resources = criteria.list();
        return resources;
    }

    @Override
    public Resource findByName(String name) {
        Criteria criteria = getSession().createCriteria(Resource.class);
        criteria.add(Restrictions.eq("name", name).ignoreCase());
        Object userObj = criteria.uniqueResult();
        Resource resource = null;
        if (userObj != null) {
            resource = (Resource) userObj;
        }
        return resource;
    }
}
