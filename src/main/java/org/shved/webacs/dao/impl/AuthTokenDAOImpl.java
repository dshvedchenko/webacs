package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.AuthTokenDAO;
import org.shved.webacs.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Transactional()
public class AuthTokenDAOImpl extends AbstractDao<String, AuthToken> implements AuthTokenDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public AuthTokenDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public AuthToken getAuthToken(String tokenVal) {
        return getByKey(tokenVal, AuthToken.class);
    }

    @Override
    public void saveToken(AuthToken token) {
        persist(token);
    }
}
