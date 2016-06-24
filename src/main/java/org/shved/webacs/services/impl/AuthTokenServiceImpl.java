package org.shved.webacs.services.impl;

import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dao.AuthTokenDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.exception.TokenException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author dshvedchenko on 6/24/16.
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {


    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AuthTokenDAO authTokenDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserAuthDTO restLogin(UserAuthDTO userLogin) {
        UserAuthDTO result = new UserAuthDTO();
        AppUser appUser = appUserDAO.findByUsername(userLogin.getUsername());

        if (passwordEncoder.matches(userLogin.getPassword(), appUser.getPassword())) {
            AuthToken token = generateNewAuthToken(appUser);
            authTokenDAO.saveToken(token);
            result.setToken(token.getToken());
            result.setUsername(userLogin.getUsername());
        }
        return result;
    }

    @Override
    @Transactional
    public void restLogout(String token) {
        try {
            authTokenDAO.deleteTokenByVal(token);
        } catch (Exception e) {
            throw new TokenException("TOKEN DELETE");
        }
    }

    @Override
    public AuthToken isTokenValid(String tokenStr) {
        AuthToken token = authTokenDAO.getAuthToken(tokenStr);

        if (token == null) {
            throw new TokenException("TOKEN NOT FOUND");
        }

        handleTokenExpired(token);

        return token;
    }

    private void handleTokenExpired(AuthToken token) {
        Calendar expirty = Calendar.getInstance();
        expirty.add(Calendar.HOUR, -2);

        if (expirty.before(token.getLastUsed())) {
            throw new TokenException("TOKEN EXPIRED");
        }
    }

    private AuthToken generateNewAuthToken(AppUser appUser) {
        AuthToken token = new AuthToken();
        token.setAppUser(appUser);
        token.setToken(UUID.randomUUID().toString());
        token.setLastUsed(new Date());
        return token;
    }

}
