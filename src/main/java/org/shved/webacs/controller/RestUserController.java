package org.shved.webacs.controller;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.shved.webacs.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

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
    public ResponseData<AuthToken> getUserById(
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
    public ResponseData<AuthToken> saveUser(
            @RequestHeader(name = "X-AUTHID") String token,
            @PathVariable(value = "userId") Long userId,
            @RequestBody AppUserDTO appUserDTO
    ) {
        authTokenService.isTokenValid(token);
        appUserService.handleSaveEditedAppUser(appUserDTO);
        ResponseData rd = new ResponseData();
        rd.setData(true);
        return rd;
    }
    //create

    //delete

}
