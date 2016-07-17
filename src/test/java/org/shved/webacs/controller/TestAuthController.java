package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dao.IAuthTokenDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
import org.shved.webacs.services.IAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestAuthController extends AbstractAppTest {


    @Autowired
    private IAppUserDAO appUserDAO;

    @Autowired
    private IAuthTokenService authTokenService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLogin() throws Exception {

        String resp = getTokenInfo();
        Assert.assertTrue(authTokenService.isTokenValid(resp));
    }


    @Test
    public void testBadLogin() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("eeeee");
        mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(contentType))
        ;
    }

    @Test
    public void testLogout() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        mockMvc.perform(post("/api/v1/logout")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(contentType))
        ;
    }


}
