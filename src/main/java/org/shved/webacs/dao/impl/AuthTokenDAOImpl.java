package org.shved.webacs.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IAuthTokenDAO;
import org.shved.webacs.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Transactional(propagation = Propagation.SUPPORTS)
public class AuthTokenDAOImpl extends AbstractDao<String, AuthToken> implements IAuthTokenDAO {

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
    public void save(AuthToken token) {
        persist(token);
    }

    @Override
    public void deleteTokenByVal(String token) {
        AuthToken authToken = getAuthToken(token);
        if (token != null) {
            getSession().delete(authToken);
        }
    }

    @Override
    public void updateToken(String token) {
        AuthToken authToken = getAuthToken(token);
        if (token != null) {
            authToken.setLastUsed(new Date());
            persist(authToken);
        }
    }

    @Override
    public AuthToken findNonExpiredByUserId(Long userId, Date validPoint) {
        List<AuthToken> lst = getSession().createCriteria(AuthToken.class)
                .createAlias("appUser", "u")
                .add(Restrictions.conjunction(
                        Restrictions.eq("u.id", userId),
                        Restrictions.ge("lastUsed", validPoint)
                )).addOrder(Order.desc("lastUsed")).list();
        return lst.size() > 0 ? lst.get(0) : null;
    }

}
