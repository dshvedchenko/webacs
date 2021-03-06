package org.shved.webacs.controller;

import org.shved.webacs.constants.Auth;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
@RequestMapping(value = RestEndpoints.API_V1_USERS)
public class RestUserController extends AbstractAPIV1Controller {
    @Autowired
    IAppUserService appUserService;

    @Autowired
    IAuthTokenService authTokenService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseData<AppUserDTO> getUserById(
            @PathVariable(value = "userId") Long userId
    ) {
        AppUserDTO appUserDTO = appUserService.getAppUserById(userId);
        ResponseData rd = new ResponseData();
        rd.setData(appUserDTO);
        return rd;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseData<AppUserDTO> getCurrentUser(
    ) {
        AppUserDTO appUserDTO = appUserService.getCurrentUser();
        ResponseData rd = new ResponseData();
        rd.setData(appUserDTO);
        return rd;
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveUser(
            @RequestBody AppUserDTO appUserDTO
    ) {
        appUserService.handleSaveEditedAppUser(appUserDTO);
    }

    @PreAuthorize(Auth.hasAdminAutority)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<AuthToken> createUser(
            @RequestBody UserCreationDTO userCreationDTO
    ) {
        AppUser appUser = appUserService.createAppUserByAdmin(userCreationDTO);
        ResponseData rd = new ResponseData();
        rd.setData(appUser);
        return rd;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(
            @PathVariable(value = "userId") Long userId
    ) {
        appUserService.deleteById(userId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseData<AppUserDTO> getAll(
    ) {
        List<AppUserDTO> listUsers = appUserService.getAll();
        ResponseData rd = new ResponseData();
        rd.setData(listUsers);
        return rd;
    }

    @RequestMapping(params = "enabled=false", method = RequestMethod.GET)
    public ResponseData<List<AppUserDTO>> getAllDisabled(
    ) {
        List<AppUserDTO> listUsers = appUserService.getAllDisabled();
        return new ResponseData(listUsers);
    }

}
