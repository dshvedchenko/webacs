package org.shved.webacs.config;

import org.shved.webacs.security.JwtAuthenticationEntryPoint;
import org.shved.webacs.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dshvedchenko on 6/14/16.
 * info sources :
 * https://www.future-processing.pl/blog/exploring-spring-boot-and-spring-security-custom-token-based-authentication-of-rest-services-with-spring-security-and-pinch-of-spring-java-configuration-and-spring-integration-testing/
 * https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/config/WebSecurityConfig.java
 */
@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationEntryPoint getUnauthorizedHandler() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(getUnauthorizedHandler()).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/api/v1/**").anonymous() // needed for browser preflight checks
                    .antMatchers(
                            HttpMethod.GET,
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/client/**",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js"
                    ).permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/login").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/v1/register").anonymous()
                    .antMatchers("/api/**")
                    .authenticated()

            ;
            http.addFilterBefore(getTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            http.headers().cacheControl();
        }

    @Bean(name = "tokenAuthenticationFilter")
    public TokenAuthenticationFilter getTokenAuthenticationFilter() throws Exception {
        TokenAuthenticationFilter restTokenAuthenticationFilter = new TokenAuthenticationFilter();
        restTokenAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return restTokenAuthenticationFilter;
    }



}
