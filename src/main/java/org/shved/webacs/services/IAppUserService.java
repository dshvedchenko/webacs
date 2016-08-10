package org.shved.webacs.services;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;

import java.util.List;

/**
 * @author dshvedchenko on 6/17/16.
 */
public interface IAppUserService {
    List<AppUserDTO> getAll();

    List<AppUserDTO> getAllEnabled();

    List<AppUserDTO> getAllDisabled();


    AppUser registerUser(UserRegistrationDTO appUser);


    AppUserDTO getAppUserById(Long userId);

    AppUserDTO getCurrentUser();

    void handleSaveEditedAppUser(AppUserDTO appUserDTO);

    AppUser createAppUserByAdmin(UserCreationDTO appUser);

    void deleteById(Long userId);

}
