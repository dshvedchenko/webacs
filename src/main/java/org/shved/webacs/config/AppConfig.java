package org.shved.webacs.config;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@EnableWebMvc //<mvc:annotation-driven />
@Configuration
@EnableTransactionManagement
@ComponentScan({"org.shved.webacs.controller"})
@Import({SecurityConfig.class, MessageConverterConfig.class, ValidatorsConfig.class, HibernateConfig.class, ServiceConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {

    public static final int CACHE_PERIOD = 31556926;
    Logger logger = LoggerFactory.logger(AppConfig.class);

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .useJaf(false)
                .ignoreAcceptHeader(false) //set to false, hope help with rest
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .defaultContentType(MediaType.TEXT_HTML);
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager manager) {

        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/pages/");
        resourceViewResolver.setSuffix(".jsp");
        resourceViewResolver.setViewClass(JstlView.class);
        resolvers.add(resourceViewResolver);

        JsonViewResolver jsonViewResolver = new JsonViewResolver();
        resolvers.add(jsonViewResolver);

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setViewResolvers(resolvers);
        viewResolver.setContentNegotiationManager(manager);
        return viewResolver;

    }

    //<mvc:resources mapping="/resources/**" location="/resources/"/>
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(CACHE_PERIOD);
        registry.addResourceHandler("/client/**").addResourceLocations("/client/").setCachePeriod(CACHE_PERIOD);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "messageSource")
    public MessageSource createMessageSourceBean() {
        ResourceBundleMessageSource msgSrc = new ResourceBundleMessageSource();
        msgSrc.setBasenames(new String[]{"messages/validation", "messages/message"});
        return msgSrc;
    }

    public static class JsonViewResolver implements ViewResolver {
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            view.setPrettyPrint(true);
            return view;
        }
    }

}