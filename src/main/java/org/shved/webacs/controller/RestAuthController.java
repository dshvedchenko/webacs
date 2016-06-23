package org.shved.webacs.controller;

import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/17/16.
 */
@RestController
public class RestAuthController {

    @Autowired
    AppUserService appUserService;

    @RequestMapping(value = "/api/login", consumes = "application/json", method = RequestMethod.POST)
    public ResponseData<AuthToken> login(
            @RequestBody UserAuthDTO userLogin
    ) {
        ResponseData rd = new ResponseData();
        UserAuthDTO authRes = null;
        authRes = appUserService.restLogin(userLogin);

        rd.setData(authRes);
        return rd;
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public ResponseData<AuthToken> logout(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        ResponseData rd = new ResponseData();
        appUserService.restLogout(token);

        rd.setData("success");
        return rd;
    }

    @RequestMapping(value = "/api/testdata", method = RequestMethod.POST)
    public ResponseData<AuthToken> getTestData(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        ResponseData rd = new ResponseData();
        rd.setData(appUserService.getTestData(token));
        return rd;
    }
}
