package org.shved.webacs.controller;

import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.exception.EmailExistsException;
import org.shved.webacs.exception.UserExistsException;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.services.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author dshvedchenko on 6/16/16.
 */
@Controller()
public class RegistrationController {
    @Autowired
    IAppUserService appUserService;

    @Autowired
    @Qualifier("passwordValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public ModelAndView registerAppUser(Model model) {
        return new ModelAndView("users/register", "user", new UserRegistrationDTO());
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerAppUser(@ModelAttribute("user") @Valid UserRegistrationDTO userDTO,
                                        BindingResult result, WebRequest request, Errors errors) {
        AppUser registered = null;
        if (!result.hasErrors()) {

            try {
                registered = createAppUser(userDTO, result);
            } catch (EmailExistsException e) {
                result.rejectValue("email", "message.emailExists");
            } catch (UserExistsException e) {
                result.rejectValue("username", "message.userExists");
            }
        }

        if (registered == null) {
            result.rejectValue("username", "message.regError");
        }

        if (result.hasErrors()) {
            return new ModelAndView("users/register", "user", userDTO);
        } else {
            return new ModelAndView("successRegister", "user", userDTO);
        }

    }

    private AppUser createAppUser(UserRegistrationDTO userDto, BindingResult result) {
        AppUser registered = null;

        registered = appUserService.registerUser(userDto);


        return registered;
    }

}
