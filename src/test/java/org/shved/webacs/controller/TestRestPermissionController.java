package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.dto.ResourceDTO;
import org.shved.webacs.dto.UserAuthDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 6/26/16.
 */
public class TestRestPermissionController extends AbstractAppTest {

    public static final String PASSWORD = "1qaz2wsx";

    private MockMvc mockMvc;
    private String userName = "admin";

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Transactional
    @Test
    public void getAll() throws Exception {

        UserAuthDTO loginInfo = new UserAuthDTO();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(PASSWORD);
        ResultActions res = mockMvc.perform(post("/api/v1/login")
                .content(this.json(loginInfo))
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());

        String tokenStr = JsonPath.read(res.andReturn().getResponse().getContentAsString(), "$.data.token");

        ResultActions response = mockMvc.perform(
                get("/api/v1/permission/list")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(8)))
                .andExpect(jsonPath("$.data[0].title", is("Reader")))
                .andExpect(jsonPath("$.data[7].title", is("Owner")));

    }


}