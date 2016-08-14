package org.shved.webacs.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author dshvedchenko on 6/10/16.
 */

public abstract class AbstractDao<PK extends Serializable, T> {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    protected T getByKey(PK key, Class<T> clazz) {
        return getSession().get(clazz, key);
    }

    protected void persist(T entity) {
        Session session = getSession();
        session.persist(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria(Class<T> clazz) {
        return getSession().createCriteria(clazz);
    }

}