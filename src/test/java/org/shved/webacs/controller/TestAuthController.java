package org.shved.webacs.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestAuthController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";


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


}
