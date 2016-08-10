package org.shved.webacs.controller;

import org.shved.webacs.constants.Auth;
import org.shved.webacs.dto.LoggedUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshvedchenko on 6/17/16.
 */
@RestController
public class RestAuthController extends AbstractAPIV1Controller {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IAppUserService appUserService;


    //TODO add link to logout
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<LoggedUserDTO> login(
            @RequestBody UserAuthDTO userLogin
    ) throws InterruptedException {
        LoggedUserDTO authRes = null;
        authRes = authTokenService.restLogin(userLogin);
        return ResponseEntity.ok(authRes);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(
            @RequestHeader(name = Auth.AUTH_TOKEN_NAME) String token
    ) {
        // ResponseData rd = new ResponseData();
        authTokenService.restLogout(token);
        return;
    }

}
