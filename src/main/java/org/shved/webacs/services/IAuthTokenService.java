package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.LoggedUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.AuthToken;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author dshvedchenko on 6/24/16.
 */
public interface IAuthTokenService {
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    void evictExpiredTokens();

    LoggedUserDTO restLogin(UserAuthDTO userLogin);
    boolean isTokenValid(String token);
    AppUserDTO getUserByToken(String tokenRaw);
    void restLogout(String token);
}
