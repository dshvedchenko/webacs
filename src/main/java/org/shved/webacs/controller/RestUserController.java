package org.shved.webacs.controller;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.shved.webacs.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/user", consumes = "application/json", produces = "application/json")
public class RestUserController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    AuthTokenService authTokenService;

    /**
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
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
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = "application/json")
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

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
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
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "userId") Long userId
    ) {
        authTokenService.isTokenValid(token);
        appUserService.deleteById(userId);
    }

}
