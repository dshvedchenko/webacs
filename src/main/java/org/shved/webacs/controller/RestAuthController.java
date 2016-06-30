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
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

/**
 * @author dshvedchenko on 6/17/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1", consumes = "application/json", produces = "application/json")
public class RestAuthController extends AbstractAPIV1Controller {

    @Autowired
    IAuthTokenService authTokenService;

    @Autowired
    IAppUserService appUserService;


    //TODO add link to logout
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseData<AuthToken> login(
            @RequestBody UserAuthDTO userLogin, UriComponentsBuilder ucb
    ) {
        ResponseData rd = new ResponseData();
        UserAuthDTO authRes = null;
        authRes = authTokenService.restLogin(userLogin);

        rd.setData(authRes);
        rd.add(linkTo(methodOn(RestAuthController.class).login(userLogin, ucb)).withSelfRel());
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

//    @Autowired
//    @Qualifier("passwordValidator")
//    private Validator validator;
//
//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        binder.addValidators(validator); //? only this validator fires
//    }
//
//    //TODO : http://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/

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
