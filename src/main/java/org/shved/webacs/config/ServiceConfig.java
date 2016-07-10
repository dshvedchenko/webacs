package org.shved.webacs.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dshvedchenko on 7/8/16.
 */
@Configuration
@ComponentScan({"org.shved.webacs.services"})
public class ServiceConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
