package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/17/16.
 */
public interface AppUserService {
    List<AppUserDTO> getAll();

    @Transactional
    AppUser registerUser(UserRegistrationDTO appUser);

    UserAuthDTO restLogin(UserAuthDTO userLogin);
    AuthToken isTokenValid(String token);

    void restLogout(String token);

    String getTestData(String token);
}
