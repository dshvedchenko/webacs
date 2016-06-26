package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.dao.IAppUserDAO;
import org.shved.webacs.dto.UserAuthDTO;
import org.shved.webacs.dto.UserCreationDTO;
import org.shved.webacs.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;
    private String userName = "admin";
    @Autowired
    private IAppUserDAO appUserDAO;

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


    @Transactional
    @Test
    public void editUserTest() throws Exception {
        final Long EDIT_USERS_ID = 2L;
        final String EDIT_USER_USERNAME = "johns";
        final String EDIT_USER_LASTNAME = "Salivan";
        final String EDIT_USER_FIRSTNAME = "John";
        final String EDIT_USER_EMAIL = "johns@example.com";
        final SysRole EDIT_USER_SYSROLE = SysRole.GENERIC;

        final String EDIT_USER_LASTNAME_NEW = "Jonnys";
        final String EDIT_USER_EMAIL_NEW = "johns12@gmail.example.com";
        final SysRole EDIT_USER_SYSROLE_NEW = SysRole.ADMIN;
        final Boolean EDIT_USER_ENABLED_NEW = false;

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

        ResultActions resUserById = mockMvc.perform(get("/api/v1/user/" + EDIT_USERS_ID)
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is(EDIT_USER_USERNAME)))
                .andExpect(jsonPath("$.data.lastname", is(EDIT_USER_LASTNAME)))
                .andExpect(jsonPath("$.data.firstname", is(EDIT_USER_FIRSTNAME)))
                .andExpect(jsonPath("$.data.email", is(EDIT_USER_EMAIL)))
                .andExpect(jsonPath("$.data.sysrole", is(EDIT_USER_SYSROLE.name())));

        Map userRecord = JsonPath.read(resUserById.andReturn().getResponse().getContentAsString(), "$.data");
        userRecord.replace("firstname", EDIT_USER_LASTNAME_NEW);
        userRecord.replace("email", EDIT_USER_EMAIL_NEW);
        userRecord.replace("sysrole", EDIT_USER_SYSROLE_NEW);
        userRecord.replace("enabled", EDIT_USER_ENABLED_NEW);

        mockMvc.perform(put("/api/v1/user/" + EDIT_USERS_ID)
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType)
                .content(new ObjectMapper().writeValueAsString(userRecord))
        ).andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/user/" + EDIT_USERS_ID)
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is(EDIT_USER_USERNAME)))
                .andExpect(jsonPath("$.data.lastname", is(EDIT_USER_LASTNAME)))
                .andExpect(jsonPath("$.data.firstname", is(EDIT_USER_LASTNAME_NEW)))
                .andExpect(jsonPath("$.data.email", is(EDIT_USER_EMAIL_NEW)))
                .andExpect(jsonPath("$.data.sysrole", is(EDIT_USER_SYSROLE_NEW.name())))
                .andExpect(jsonPath("$.data.enabled", is(EDIT_USER_ENABLED_NEW)));
    }

    @Transactional
    @Test
    public void createUserTest() throws Exception {
        Long newuserId;
        final String NEW_USER_USERNAME = "user32";
        final String NEW_USER_LASTNAME = "User32LastName";
        final String NEW_USER_FIRSTNAME = "User32FIRSTName";
        final String NEW_USER_EMAIL = "user32@example.com";
        final SysRole NEW_USER_SYSROLE = SysRole.GENERIC;
        final Boolean NEW_USER_ENABLED = true;
        final String NEW_USER_PASSWORD = "2wsx3edc";
        final String NEW_USER_PASSWORD_ENC = passwordEncoder.encode(NEW_USER_PASSWORD);

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

        UserCreationDTO appUserDTO = new UserCreationDTO();
        appUserDTO.setUsername(NEW_USER_USERNAME);
        appUserDTO.setFirstname(NEW_USER_FIRSTNAME);
        appUserDTO.setLastname(NEW_USER_LASTNAME);
        appUserDTO.setEmail(NEW_USER_EMAIL);
        appUserDTO.setEnabled(NEW_USER_ENABLED);
        appUserDTO.setSysrole(NEW_USER_SYSROLE);
        appUserDTO.setPassword(NEW_USER_PASSWORD);


        ResultActions response = mockMvc.perform(
                post("/api/v1/user")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(appUserDTO))
        )
                .andExpect(status().isCreated());

        Integer newUserId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");

        mockMvc.perform(
                get("/api/v1/user/" + newUserId)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is(NEW_USER_USERNAME)))
                .andExpect(jsonPath("$.data.lastname", is(NEW_USER_LASTNAME)))
                .andExpect(jsonPath("$.data.firstname", is(NEW_USER_FIRSTNAME)))
                .andExpect(jsonPath("$.data.email", is(NEW_USER_EMAIL)))
                .andExpect(jsonPath("$.data.sysrole", is(NEW_USER_SYSROLE.name())))
                .andExpect(jsonPath("$.data.enabled", is(NEW_USER_ENABLED)));
    }

    @Transactional
    @Test
    public void deleteUserTest() throws Exception {
        final Long EDIT_USERS_ID = 2L;
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


        mockMvc.perform(delete("/api/v1/user/" + EDIT_USERS_ID)
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType)

        ).andExpect(status().isAccepted());

        mockMvc.perform(get("/api/v1/user/" + EDIT_USERS_ID)
                .header("X-AUTHID", tokenStr)
                .accept(contentType)
                .contentType(contentType))
                .andExpect(status().isNotFound())
        ;
    }


}
