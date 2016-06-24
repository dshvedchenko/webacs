package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dao.AuthTokenDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.exception.*;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.services.AppUserService;
import org.shved.webacs.services.AuthTokenService;
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
    private AuthTokenService authTokenService;

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
    public String getTestData(String token) {
        authTokenService.isTokenValid(token);

        return "TEST COMPLETED";
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
