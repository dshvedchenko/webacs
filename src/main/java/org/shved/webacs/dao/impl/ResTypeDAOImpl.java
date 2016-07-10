package org.shved.webacs.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IResTypeDAO;
import org.shved.webacs.model.ResType;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
@Repository
public class ResTypeDAOImpl extends AbstractDao<Integer, ResType> implements IResTypeDAO {

    @Override
    public List<ResType> findAll() {
        List<ResType> resources = getSession().createCriteria(ResType.class)
                .list();
        return resources;
    }

    @Override
    public ResType findById(Integer id) {
        return getByKey(id, ResType.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(ResType resType) {
        persist(resType);
        getSession().flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteById(Integer id) {
        getSession().delete(getSession().get(ResType.class, id));
    }


    @Override
    public ResType findByName(String name) {
        ResType resType = null;
        Criteria criteria = getSession().createCriteria(ResType.class)
                .add(Restrictions.eq("name", name));
        resType = (ResType) criteria.uniqueResult();
        return resType;
    }
}