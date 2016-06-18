package org.shved.webacs.services;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dao.AuthTokenDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserLoginDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.exception.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AuthTokenDAO authTokenDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AppUserDTO> getAll() {
        List<AppUser> appUsers = appUserDAO.findAllAppUsers();
        return getAppUserDtoList(appUsers);
    }

    @Override
    @Transactional
    public AppUser registerUser(UserRegistrationDTO newUser) {

        isNewUserValid(newUser);

        AppUser appUser = modelMapper.map(newUser, AppUser.class);
        appUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        appUser.setSysrole(1);
        appUser.setEnabled(true);

        try {
            appUserDAO.saveAppUser(appUser);
        } catch (Exception e) {
            throw new AppException("Can not save new user", e);
        }

        return appUser;
    }

    @Override
    @Transactional
    public String restLogin(UserLoginDTO userLogin) {
        String result = null;
        AppUser appUser = appUserDAO.findByUsername(userLogin.getUsername());

        if (passwordEncoder.matches(userLogin.getPassword(), appUser.getPassword())) {
            AuthToken token = generateNewAuthToken(appUser);
            authTokenDAO.saveToken(token);
            result = token.getToken();
        }
        return result;
    }

    @Override
    public AuthToken isTokenValid(String tokenStr) {
        AuthToken token = authTokenDAO.getAuthToken(tokenStr);

        if (token == null) {
            throw new TokenNotExistsException();
        }

        handleTokenExpired(token);

        return token;
    }

    private void handleTokenExpired(AuthToken token) {
        Calendar expirty = Calendar.getInstance();
        expirty.add(Calendar.HOUR, -2);

        if (expirty.before(token.getLastUsed())) {
            throw new TokenExpiredException();
        }
    }

    private AuthToken generateNewAuthToken(AppUser appUser) {
        AuthToken token = new AuthToken();
        token.setAppUser(appUser);
        token.setToken(UUID.randomUUID().toString());
        token.setLastUsed(new Date());
        return token;
    }

    private void isNewUserValid(UserRegistrationDTO newUser) {
        if (emailExist(newUser.getEmail()))
            throw new EmailExistsException();

        if (userExist(newUser.getUsername()))
            throw new UserExistsException();
    }

    private List<AppUserDTO> getAppUserDtoList(List<AppUser> appUserList) {
        return appUserList.stream().map(item -> modelMapper.map(item, AppUserDTO.class)).collect(Collectors.toList());
    }


    private boolean emailExist(String email) {
        return appUserDAO.findByEmail(email) != null;
    }

    private boolean userExist(String username) {
        return appUserDAO.findByUsername(username) != null;
    }

}
