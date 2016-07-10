package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IAuthTokenDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.exception.TokenException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.services.IAuthTokenService;
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
public class AuthTokenServiceImpl implements IAuthTokenService {


    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private IAppUserDAO appUserDAO;
    @Autowired
    private IAuthTokenDAO authTokenDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserAuthDTO restLogin(UserAuthDTO userLogin) {
        UserAuthDTO result = new UserAuthDTO();
        AppUser appUser = appUserDAO.findByUsername(userLogin.getUsername());

        if (passwordEncoder.matches(userLogin.getPassword(), appUser.getPassword())) {
            AuthToken token = null;
            token = authTokenDAO.findNonExpiredByUserId(appUser.getId(), getValidPoint());
            if (token == null) {
                token = generateNewAuthToken(appUser);
            } else {
                token.setLastUsed(new Date());
            }
            authTokenDAO.save(token);
            result.setToken(token.getToken());
            result.setUsername(userLogin.getUsername());
        }
        if (result.getToken() == null) throw new TokenException();
        return result;
    }

    Date getValidPoint() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        return cal.getTime();
    }

    @Override
    @Transactional
    public void restLogout(String token) {
        try {
            authTokenDAO.deleteTokenByVal(token);
        } catch (Exception e) {
            throw new TokenException("TOKEN DELETE issue");
        }
    }

    @Override
    @Transactional
    public boolean isTokenValid(String rawToken) {
        AuthToken token = authTokenDAO.getAuthToken(rawToken);

        if (token == null) {
            throw new TokenException("TOKEN NOT FOUND");
        }
        handleTokenExpired(token);
        return true;
    }

    @Override
    @Transactional
    public AppUserDTO getUserByToken(String rawToken) {
        AuthToken token = authTokenDAO.getAuthToken(rawToken);
        if (token == null) {
            return null;
        }
        return modelMapper.map(token.getAppUser(), AppUserDTO.class);
    }

    private void handleTokenExpired(AuthToken token) {
        Calendar expirty = Calendar.getInstance();
        expirty.add(Calendar.HOUR, -2);

        if (expirty.before(token.getLastUsed())) {
            authTokenDAO.deleteTokenByVal(token.getToken());
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
