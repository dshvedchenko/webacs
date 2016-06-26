package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.exception.EmailExistsException;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.exception.UserExistsException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.SysRole;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private IAppUserDAO appUserDAO;

    @Autowired
    private IAuthTokenService authTokenService;

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

        AppUser appUser = modelMapper.map(newUser, AppUser.class);
        appUser.setSysrole(SysRole.GENERIC);
        appUser.setEnabled(true);

        return addNewAppUser(appUser);
    }

    private AppUser addNewAppUser(AppUser appUser) {
        isNewUserValid(appUser);
        if (appUser.getPassword() == null) throw new AppException("NULL PASSWORD IS NOT ALLOWED", null);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        try {
            appUserDAO.saveAppUser(appUser);
        } catch (Exception e) {
            throw new AppException("Can not save new user", e);
        }

        return appUser;
    }

    @Override
    public AppUser createAppUserByAdmin(UserCreationDTO newUser) {
        AppUser appUser = modelMapper.map(newUser, AppUser.class);
        return addNewAppUser(appUser);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        AppUser appUser = appUserDAO.findById(userId);
        appUser.setEnabled(false);
        appUser.setDisabled_at(new Date());
        appUserDAO.saveAppUser(appUser);
    }

    @Override
    public String getTestData(String token) {
        authTokenService.isTokenValid(token);

        return "TEST COMPLETED";
    }

    @Override
    public AppUserDTO getAppUserById(Long userId) {
        AppUser appUser = appUserDAO.findById(userId);
        if (appUser == null) throw new NotFoundException();

        AppUserDTO appUserDTO = modelMapper.map(appUser, AppUserDTO.class);
        secureFilterAppUserDTO(appUserDTO);
        return appUserDTO;
    }

    private void secureFilterAppUserDTO(AppUserDTO appUserDTO) {
        //  appUserDTO.setPassword(null);
    }

    @Transactional
    @Override
    public void handleSaveEditedAppUser(AppUserDTO appUserDTO) {
        AppUser appUser = appUserDAO.findById(appUserDTO.getId());
        if (appUser == null) throw new AppException();

        if (isEmailUsedByAnotherUser(appUser, appUserDTO.getEmail())) throw new EmailExistsException();

        applyAppUserDTO2AppUserByAdmin(appUserDTO, appUser);

        appUserDAO.saveAppUser(appUser);
    }

    private void applyAppUserDTO2AppUserByAdmin(AppUserDTO appUserDTO, AppUser appUser) {
        appUser.setEnabled(appUserDTO.isEnabled());
        appUser.setEmail(appUserDTO.getEmail());
        appUser.setSysrole(appUserDTO.getSysrole());
        appUser.setFirstname(appUserDTO.getFirstname());
        appUser.setLastname(appUserDTO.getLastname());
    }

    private void isNewUserValid(AppUser newUser) {
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

    private boolean isEmailUsedByAnotherUser(AppUser currUser, String email) {
        AppUser anotherUser = appUserDAO.findByEmail(email);
        if (anotherUser == null) return false;
        return currUser.getId() != anotherUser.getId();
    }
}
