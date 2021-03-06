package org.shved.webacs.dao;

import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author dshvedchenko on 6/17/16.
 */

public interface IAuthTokenDAO {
    AuthToken getAuthToken(String tokenVal);
    void save(AuthToken token);
    void deleteTokenByVal(String token);

    void deleteTokensIssuedBefore(Date expirationDate);
    void updateToken(String token);
    AuthToken findNonExpiredByUserId(Long userId, Date validPoint);
}
