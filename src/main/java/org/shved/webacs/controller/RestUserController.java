package org.shved.webacs.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUserController {
    @Autowired
    IAppUserService appUserService;

    @Autowired
    IAuthTokenService authTokenService;

    /**
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<AppUserDTO> getUserById(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "userId") Long userId
    ) {
        authTokenService.isTokenValid(token);
        AppUserDTO appUserDTO = appUserService.getAppUserById(userId);
        ResponseData rd = new ResponseData();
        rd.setData(appUserDTO);
        return rd;
    }

    //edit - save
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "userId") Long userId,
            @RequestBody AppUserDTO appUserDTO
    ) {
        authTokenService.isTokenValid(token);
        appUserService.handleSaveEditedAppUser(appUserDTO);
    }
    //create

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<AuthToken> createUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @RequestBody UserCreationDTO userCreationDTO
    ) {
        authTokenService.isTokenValid(token);
        AppUser appUser = appUserService.createAppUserByAdmin(userCreationDTO);
        ResponseData rd = new ResponseData();
        rd.setData(appUser);
        return rd;
    }

    //delete
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "userId") Long userId
    ) {
        authTokenService.isTokenValid(token);
        appUserService.deleteById(userId);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<AppUserDTO> getAll(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<AppUserDTO> listUsers = appUserService.getAllEnabled();
        ResponseData rd = new ResponseData();
        rd.setData(listUsers);
        return rd;
    }

    @RequestMapping(value = "/listdisabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<AppUserDTO> getAllDisabled(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        authTokenService.isTokenValid(token);
        List<AppUserDTO> listUsers = appUserService.getAllDisabled();
        ResponseData rd = new ResponseData();
        rd.setData(listUsers);
        return rd;
    }

}
