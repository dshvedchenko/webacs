package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IAuthTokenDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.LoggedUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.exception.TokenException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.services.IAuthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    private static final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private IAppUserDAO appUserDAO;
    @Autowired
    private IAuthTokenDAO authTokenDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    @Transactional
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        authTokenDAO.deleteTokensIssuedBefore(getValidPoint());
    }

    @Override
    @Transactional
    public LoggedUserDTO restLogin(UserAuthDTO userLogin) {
        LoggedUserDTO result = new LoggedUserDTO();
        AppUser appUser = appUserDAO.findByUsername(userLogin.getUsername());
        if (appUser == null) throw new TokenException();

        boolean allowedToLogin = isAllowedToLogin(userLogin, appUser);

        if (allowedToLogin) {
            AuthToken token = null;
            token = authTokenDAO.findNonExpiredByUserId(appUser.getId(), getValidPoint());
            if (token == null) {
                token = generateNewAuthToken(appUser);
            } else {
                token.setLastUsed(new Date());
            }
            authTokenDAO.save(token);
            result.setToken(token.getToken());
            result.setSysrole(appUser.getSysrole());
            result.setUsername(userLogin.getUsername());
        }
        if (result.getToken() == null) throw new TokenException();
        return result;
    }

    private boolean isAllowedToLogin(UserAuthDTO userLogin, AppUser appUser) {
        return passwordEncoder.matches(userLogin.getPassword(), appUser.getPassword()) && appUser.getEnabled();
    }

    Date getValidPoint() {
        Calendar cal = getTimePointForExpiredTokens();
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
        Calendar expirty = getTimePointForExpiredTokens();

        if (expirty.before(token.getLastUsed())) {
            authTokenDAO.deleteTokenByVal(token.getToken());
            throw new TokenException("TOKEN EXPIRED");
        }
    }

    private Calendar getTimePointForExpiredTokens() {
        Calendar expirty = Calendar.getInstance();
        expirty.add(Calendar.MINUTE, -30);
        return expirty;
    }

    private AuthToken generateNewAuthToken(AppUser appUser) {
        AuthToken token = new AuthToken();
        token.setAppUser(appUser);
        token.setToken(UUID.randomUUID().toString());
        token.setLastUsed(new Date());
        return token;
    }

}
