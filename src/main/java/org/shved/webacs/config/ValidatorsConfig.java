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

    @Bean(name = "passwordValidator")
    public Validator createPasswordValidaror() {
        return new PasswordValidator();
    }

    @Bean(name = "messageSource")
    public MessageSource createMessageSourceBean() {
        ResourceBundleMessageSource msgSrc = new ResourceBundleMessageSource();
        msgSrc.setBasename("validation");
        return msgSrc;
    }
}
