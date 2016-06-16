package org.shved.webacs.validator;

import org.shved.webacs.dto.AppUserDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author dshvedchenko on 6/16/16.
 */
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AppUserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "valid.passwordConf");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "valid.passwordConf");
        AppUserDTO appUserDTO = (AppUserDTO) obj;
        if (!appUserDTO.getPassword().equals(appUserDTO.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "valid.passwordConfDiff");
        }
    }
}
