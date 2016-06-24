package org.shved.webacs.controller;

import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.AppUserService;
import org.shved.webacs.services.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/17/16.
 */
@RestController
public class RestAuthController extends AbstractAPIV1Controller {

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    AppUserService appUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseData<AuthToken> login(
            @RequestBody UserAuthDTO userLogin
    ) {
        ResponseData rd = new ResponseData();
        UserAuthDTO authRes = null;
        authRes = authTokenService.restLogin(userLogin);

        rd.setData(authRes);
        return rd;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseData<AuthToken> logout(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        ResponseData rd = new ResponseData();
        authTokenService.restLogout(token);

        rd.setData("success");
        return rd;
    }

    @RequestMapping(value = "testdata", method = RequestMethod.POST)
    public ResponseData<AuthToken> getTestData(
            @RequestHeader(name = "X-AUTHID") String token
    ) {
        ResponseData rd = new ResponseData();
        rd.setData(appUserService.getTestData(token));
        return rd;
    }
}
