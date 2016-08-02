package org.shved.webacs.controller;

import org.shved.webacs.constants.Auth;
import org.shved.webacs.dto.LoggedUserDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public ResponseData<LoggedUserDTO> login(
            @RequestBody UserAuthDTO userLogin
    ) throws InterruptedException {
        ResponseData rd = new ResponseData();
        LoggedUserDTO authRes = null;
        authRes = authTokenService.restLogin(userLogin);
        rd.setData(authRes);
        rd.add(linkTo(methodOn(RestAuthController.class).login(userLogin)).withSelfRel());
        return rd;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseData<AuthToken> logout(
            @RequestHeader(name = Auth.AUTH_TOKEN_NAME) String token
    ) {
        ResponseData rd = new ResponseData();
        authTokenService.restLogout(token);
        rd.setData("success");
        rd.add(linkTo(methodOn(RestAuthController.class).logout(token)).withSelfRel());
        return rd;
    }

}
