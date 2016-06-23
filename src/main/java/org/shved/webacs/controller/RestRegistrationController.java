package org.shved.webacs.controller;

import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dshvedchenko on 6/23/16.
 */
@RestController
public class RestRegistrationController {
    @Autowired
    AppUserService appUserService;

    @RequestMapping(value = "/api/register", consumes = "application/json", method = RequestMethod.POST)
    public ResponseData<Boolean> register(
            @RequestBody UserRegistrationDTO userLogin
    ) {
        ResponseData rd = new ResponseData();
        AppUser authRes = null;
        authRes = appUserService.registerUser(userLogin);

        rd.setData(true);
        return rd;
    }
}
