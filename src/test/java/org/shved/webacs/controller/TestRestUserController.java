package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.AppUserDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/21/16.
 */
public class TestRestUserController extends AbstractAppTest {
    private MockMvc mockMvc;

    private String userName = "admin";


    @Autowired
    private AppUserDAO appUserDAO;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Transactional
    @Test
    public void testGetUserById() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("1qaz2wsx");
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        mockMvc.perform(get("/api/v1/user/1")
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("admin")))
                .andExpect(jsonPath("$.data.lastname", is("admin")))
                .andExpect(jsonPath("$.data.firstname", is("admin")))
                .andExpect(jsonPath("$.data.email", is("admin@example.com")))
                .andExpect(jsonPath("$.data.sysrole", is(0)));


    }


    //@Transactional
    @Test
    public void editUserTest() throws Exception {
        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword("1qaz2wsx");
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResultActions resUserById = mockMvc.perform(get("/api/v1/user/2")
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("johns")))
                .andExpect(jsonPath("$.data.lastname", is("Salivan")))
                .andExpect(jsonPath("$.data.firstname", is("John")))
                .andExpect(jsonPath("$.data.email", is("johns@example.com")))
                .andExpect(jsonPath("$.data.sysrole", is(1)));

        Map userRecord = JsonPath.read(resUserById.andReturn().getResponse().getContentAsString(), "$.data");
        userRecord.replace("firstname", "Jonnys");
        userRecord.replace("email", "johns12@gmail.example.com");
        userRecord.replace("sysrole", 0);
        userRecord.remove("enabled", false);

        mockMvc.perform(put("/api/v1/user/2")
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType)
                .content(new ObjectMapper().writeValueAsString(userRecord))
        ).andExpect(status().isAccepted());
    }
}
