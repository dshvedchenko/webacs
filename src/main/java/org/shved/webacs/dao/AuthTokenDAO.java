package org.shved.webacs.dao;

import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;

/**
 * @author dshvedchenko on 6/17/16.
 */
public interface AuthTokenDAO {
    AuthToken getAuthToken(String tokenVal);
    void saveToken(AuthToken token);

    void deleteTokenByVal(String token);

    void updateToken(String token);
}
