package org.shved.webacs.validator;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author dshvedchenko on 6/16/16.
 */
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "valid.passwordConf");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "valid.passwordConf");
        UserRegistrationDTO dto = (UserRegistrationDTO) obj;
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "valid.passwordConfDiff");
        }
    }
}
