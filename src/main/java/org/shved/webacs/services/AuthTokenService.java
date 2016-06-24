package org.shved.webacs.services;

import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.AuthToken;

/**
 * @author dshvedchenko on 6/24/16.
 */
public interface AuthTokenService {
    UserAuthDTO restLogin(UserAuthDTO userLogin);

    AuthToken isTokenValid(String token);

    void restLogout(String token);
}
