package org.shved.webacs.controller;

import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.AuthToken;
import org.shved.webacs.response.ResponseData;
import org.shved.webacs.services.IAppUserService;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author dshvedchenko on 6/17/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1", consumes = "application/json", produces = "application/json")
public class RestRegistrationController extends AbstractAPIV1Controller {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IAppUserService appUserService;


    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Boolean> register(
            @RequestBody @Valid UserRegistrationDTO regInfo,
            BindingResult result, WebRequest request, Errors errors
    ) {
        ResponseData rd = new ResponseData();
        AppUser authRes = null;
        authRes = appUserService.registerUser(regInfo);

        rd.setData(true);
        return rd;
    }

}
