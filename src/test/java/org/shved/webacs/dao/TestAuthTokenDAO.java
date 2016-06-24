package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestAuthTokenDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestAuthTokenDAO.class);

    @Autowired
    private AuthTokenDAO authTokenDAO;

    @Autowired
    private AppUserDAO appUserDAO;


    @Test
    public void daoExists() {
        Assert.assertNotNull(authTokenDAO);
    }

    @Test
    public void findAllResources() {
        AuthToken token = authTokenDAO.getAuthToken("ddddd");
        Assert.assertNull(token);
    }

    @Test
    public void saveToken() {
        AppUser user = appUserDAO.findByUsername("admin");
        AuthToken token = new AuthToken();
        String tokenStr = UUID.randomUUID().toString();
        token.setAppUser(user);
        token.setToken(tokenStr);
        token.setLastUsed(new Date());
        authTokenDAO.saveToken(token);

        AuthToken token2 = authTokenDAO.getAuthToken(tokenStr);
        Assert.assertNotNull(token2);
        Assert.assertEquals(tokenStr, token2.getToken());
    }


    @Test
    public void deleteToken() {
        AppUser user = appUserDAO.findByUsername("admin");
        AuthToken token = new AuthToken();
        String tokenStr = UUID.randomUUID().toString();
        token.setAppUser(user);
        token.setToken(tokenStr);
        token.setLastUsed(new Date());
        authTokenDAO.saveToken(token);
        authTokenDAO.deleteTokenByVal(tokenStr);

        AuthToken token2 = authTokenDAO.getAuthToken(tokenStr);
        Assert.assertNull(token2);

    }

}
