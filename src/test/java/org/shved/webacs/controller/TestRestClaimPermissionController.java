package org.shved.webacs.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 7/5/16.
 */
public class TestRestClaimPermissionController extends AbstractAppTest {


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void getAllClaimsTest() throws Exception {
        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get("/api/v1/claim/list")
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(14)))
                .andExpect(jsonPath("$.data[0].approver.username", is("billk")))
                .andExpect(jsonPath("$.data[0].user.username", is("ninaa")))
                .andExpect(jsonPath("$.data[0].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].grantedAt", is("2016-06-10 10:20:00.000+0000")))
                .andExpect(jsonPath("$.data[13].approver.username", is("admin")))
                .andExpect(jsonPath("$.data[13].user.username", is("billk")))
                .andExpect(jsonPath("$.data[13].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[13].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[13].grantedAt", is("2016-06-10 10:20:00.000+0000")));
    }
}
