package org.shved.webacs.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author dshvedchenko on 6/16/16.
 */
@Configuration
public class MessageConverterConfig {
    @Bean(name = "jsonMessageConverter")
    public HttpMessageConverter createJsonMessageConverter() {
        HttpMessageConverter converter = new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter();
        return converter;
    }



}
