package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/17/16.
 */
public interface IAppUserService {
    List<AppUserDTO> getAll();

    List<AppUserDTO> getAllEnabled();

    List<AppUserDTO> getAllDisabled();

    @Transactional
    AppUser registerUser(UserRegistrationDTO appUser);
    String getTestData(String token);

    @Transactional
    AppUserDTO getAppUserById(Long userId);

    @Transactional
    AppUserDTO getCurrentUser();

    @Transactional
    void handleSaveEditedAppUser(AppUserDTO appUserDTO);

    @Transactional
    AppUser createAppUserByAdmin(UserCreationDTO appUser);

    @Transactional
    void deleteById(Long userId);

}
