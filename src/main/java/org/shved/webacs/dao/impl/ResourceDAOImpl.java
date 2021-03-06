package org.shved.webacs.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
@Repository("resourceDAO")
public class ResourceDAOImpl extends AbstractDao<Long, Resource> implements IResourceDAO {

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
    public void save(Resource resource) {
        persist(resource);
        getSession().flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteById(Long id) {
        getSession().delete(getSession().get(Resource.class, id));
    }

    @Override
    public List<Resource> findAllByTypeName(String typeName) {
        Criteria criteria = getSession().createCriteria(Resource.class)
                .createAlias("resType", "rt")
                .add(Restrictions.eq("rt.name", typeName).ignoreCase());
        List<Resource> resources = criteria.list();
        return resources;
    }

    @Override
    public List<Resource> findAllByResTypeId(Integer typeId) {
        Criteria criteria = getSession().createCriteria(Resource.class)
                .createAlias("resType", "rt")
                .add(Restrictions.eq("rt.id", typeId));
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
