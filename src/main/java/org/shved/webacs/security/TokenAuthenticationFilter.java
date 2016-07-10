package org.shved.webacs.security;

import org.shved.webacs.dto.AppUserDTO;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * @author dshvedchenko on 7/8/16.
 */

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAuthTokenService authTokenService;

    @Autowired
    private UserDetailsService userDetailsService;


    @Value("X-AUTHID")
    private String tokenHeader;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Optional<String> authToken = Optional.ofNullable(httpRequest.getHeader(this.tokenHeader));
        if (!authToken.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {

            String username = getUsernameByToken(authToken.get());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (authTokenService.isTokenValid(authToken.get())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }


    private String getUsernameByToken(String authToken) {
        AppUserDTO appUserDTO = authTokenService.getUserByToken(authToken);
        if (appUserDTO == null) throw new NotFoundException("TOKEN NOT VALID, not user data");

        return appUserDTO.getUsername();
    }
}
