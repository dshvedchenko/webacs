package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/26/16.
 */
public class TestRestResourceController extends AbstractAppTest {

    private MockMvc mockMvc;
    private String userName = "admin";

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Transactional
    @Test
    public void createUserTest() throws Exception {

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

        ResourceDTO rdto = new ResourceDTO();
        rdto.setKind("time");
        rdto.setName("future");
        rdto.setDetail("my future");


        ResultActions response = mockMvc.perform(
                post("/api/v1/resource")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rdto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists());

        Integer newResourceId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");

    }
}