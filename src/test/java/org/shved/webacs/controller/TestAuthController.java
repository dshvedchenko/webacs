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
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("1qaz2wsx");
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String resp = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");
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
    @Transactional
    public void testRegisterUser() throws Exception {
        UserRegistrationDTO regInfo = new UserRegistrationDTO();
        String username = UUID.randomUUID().toString();
        String email = username + "_test.com";
        regInfo.setUsername(username);
        regInfo.setPassword("1qaz2wsx");
        regInfo.setEmail(email);
        regInfo.setFirstName("UserJ");
        regInfo.setLastName("UserF");

        ResultActions res = mockMvc.perform(post("/api/v1/register")
                .content(this.json(regInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(true));


        AppUser au = appUserDAO.findByUsername(username);
        Assert.assertNotNull(au);
        Assert.assertEquals(username, au.getUsername());
        Assert.assertEquals(email, au.getEmail());
        Assert.assertEquals("UserJ", au.getFirstname());
        Assert.assertEquals("UserF", au.getLastname());
    }


}
