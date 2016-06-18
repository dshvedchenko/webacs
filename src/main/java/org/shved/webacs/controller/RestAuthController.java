package org.shved.webacs.controller;

import org.shved.webacs.dto.UserLoginDTO;
import org.shved.webacs.exception.TokenExpiredException;
import org.shved.webacs.exception.TokenNotExistsException;
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
            @RequestBody UserLoginDTO userLogin
    ) {
        ResponseData rd = new ResponseData();
        String token = null;
        try {
            token = appUserService.restLogin(userLogin);
        } catch (Exception tee) {
            Error error = new Error();
            error.setStatus(405);
            error.setMessage(tee.getMessage());
        }

        rd.setData(token);
        return rd;
    }
}
