package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserRegistrationDTO;
import org.shved.webacs.model.AppUser;
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
    private MockMvc mockMvc;

    private String userName = "admin";

    @Autowired
    private AppUserDAO appUserDAO;

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
        Assert.assertEquals("eejwekfoweifwerwer", resp);

    }


    @Test
    public void testBadLogin() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("eeeee");
        mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

        ;
    }

    @Test
    //enabled to avoid : Could not obtain transaction-synchronized Session for current thread
    //also it lead that all service+ dao method executed in this transaction and all DB changes rolledback
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
                // .andExpect(jsonPath("$", hasSize(1))) ?? java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)V
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(true));


        AppUser au = appUserDAO.findByUsername(username);
        Assert.assertNotNull(au);
        Assert.assertEquals(username, au.getUsername());
        Assert.assertEquals(email, au.getEmail());
        Assert.assertEquals("UserJ", au.getFirstname());
        Assert.assertEquals("UserF", au.getLastname());
        // appUserDAO.delete(au);
    }


}
