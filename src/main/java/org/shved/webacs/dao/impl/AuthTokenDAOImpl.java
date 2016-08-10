package org.shved.webacs.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IAuthTokenDAO;
import org.shved.webacs.model.AuthToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Repository
public class AuthTokenDAOImpl extends AbstractDao<String, AuthToken> implements IAuthTokenDAO {

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
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTokensIssuedBefore(Date expirationDate) {
        Session session = getSession();
        session.createQuery("delete from AuthToken a where a.lastUsed < :expirationDate")
                .setDate("expirationDate", expirationDate)
                .executeUpdate();
        session.flush();
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
        List<AuthToken> lst;
        lst = getSession().createCriteria(AuthToken.class)
                .createAlias("appUser", "u")
                .add(Restrictions.conjunction(
                        Restrictions.eq("u.id", userId),
                        Restrictions.ge("lastUsed", validPoint)
                )).addOrder(Order.desc("lastUsed")).list();
        return lst.size() > 0 ? lst.get(0) : null;
    }

}
