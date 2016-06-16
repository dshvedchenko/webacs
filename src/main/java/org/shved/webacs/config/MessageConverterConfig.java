package org.shved.webacs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

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
