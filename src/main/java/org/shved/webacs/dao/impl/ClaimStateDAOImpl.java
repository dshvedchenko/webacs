package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.model.ClaimState;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class ClaimStateDAOImpl extends AbstractDao<Integer, ClaimState> implements IClaimStateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public ClaimStateDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ClaimState getById(Integer id) {
        return getByKey(id, ClaimState.class);
    }
}
