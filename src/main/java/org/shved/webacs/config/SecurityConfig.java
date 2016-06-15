package org.shved.webacs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author dshvedchenko on 6/14/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .antMatcher("/api/**")
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET ,"/api/**").authenticated()
//                .antMatchers(HttpMethod.POST ,"/api/**").authenticated()
//                .antMatchers(HttpMethod.DELETE ,"/api/**").authenticated()
//                .and().httpBasic()
//                .and()
//
//                .authorizeRequests()
//                .antMatchers("/secured/**").access("hasAuthority('ADMIN')")
//                .and().formLogin()
//                        .loginPage("/login")
//                        .passwordParameter("acs_password")
//                        .usernameParameter("acs_username")
//
//                .and().csrf().disable()
//                .exceptionHandling().accessDeniedPage("/Access_Denied")
//;
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                    .anyRequest().hasAnyAuthority("ADMIN", "GENERIC")
                    .and()
                    .httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/connect/**").permitAll()
                    .antMatchers("/", "/register").permitAll()
                    .antMatchers("/secured/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and() //Login Form configuration for all others
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .passwordParameter("acs_password")
                    .usernameParameter("acs_username")
                    .and() //Logout Form configuration
                    .logout().permitAll();
        }
    }

}
