package org.shved.webacs.controller;

import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.constants.RestEndpoints;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 7/17/16.
 */
public class TestRestSysRolesController extends AbstractAppTest {

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void checkSysRoleEndpoint() throws Exception {
        String tokenStr = getTokenInfo();

        mockMvc.perform(
                get(RestEndpoints.API_V1_SYSROLES)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(0)))
                .andExpect(jsonPath("$.data[0].name", is("ADMIN")))
                .andExpect(jsonPath("$.data[1].id", is(1)))
                .andExpect(jsonPath("$.data[1].name", is("GENERIC")))
        ;
    }
}
