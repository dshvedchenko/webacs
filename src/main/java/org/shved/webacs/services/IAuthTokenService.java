package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.LoggedUserDTO;
import org.shved.webacs.dto.UserAuthDTO;

/**
 * @author dshvedchenko on 6/24/16.
 */
public interface IAuthTokenService {
    int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    void evictExpiredTokens();

    LoggedUserDTO restLogin(UserAuthDTO userLogin);
    boolean isTokenValid(String token);
    AppUserDTO getUserByToken(String tokenRaw);
    void restLogout(String token);
}
