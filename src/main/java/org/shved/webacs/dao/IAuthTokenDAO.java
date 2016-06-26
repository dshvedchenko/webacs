package org.shved.webacs.dao;

import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;

import java.util.Date;

/**
 * @author dshvedchenko on 6/17/16.
 */
public interface IAuthTokenDAO {
    AuthToken getAuthToken(String tokenVal);
    void saveToken(AuthToken token);
    void deleteTokenByVal(String token);
    void updateToken(String token);

    AuthToken findNonExpiredByUserId(Long userId, Date validPoint);
}
