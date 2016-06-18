package org.shved.webacs.config;

import org.shved.webacs.validator.PasswordValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;

/**
 * @author dshvedchenko on 6/16/16.
 */
@Configuration
public class ValidatorsConfig {


    public static final String PASSWORD_VALIDATOR = "passwordValidator";

    @Bean(name = PASSWORD_VALIDATOR)
    public Validator createPasswordValidaror() {
        return new PasswordValidator();
    }


}
