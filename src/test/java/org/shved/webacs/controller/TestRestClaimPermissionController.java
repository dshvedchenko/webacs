package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.constants.Auth;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.CreatePermissionClaimDTO;
import org.shved.webacs.dto.PermissionClaimDTO;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author dshvedchenko on 7/5/16.
 */
public class TestRestClaimPermissionController extends AbstractAppTest {

    @Test
    @Transactional
    //@WithMockUser(value = "admin")
    public void getAllClaimsTest() throws Exception {
        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_CLAIMS)
                        .header(Auth.AUTH_TOKEN_NAME, tokenStr)
                        .accept(contentType)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(10)))
                .andExpect(jsonPath("$.data[0].approver.username", is("billk")))
                .andExpect(jsonPath("$.data[0].user.username", is("ninaa")))
                .andExpect(jsonPath("$.data[0].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[0].grantedAt", is("2016-06-10 10:20:00.000+0000")))
                .andExpect(jsonPath("$.data[9].approver.username", is("billk")))
                .andExpect(jsonPath("$.data[9].user.username", is("ninaa")))
                .andExpect(jsonPath("$.data[9].claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data[9].approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data[9].grantedAt", is("2016-06-10 10:20:00.000+0000")));
    }

    @Test
    @Transactional
//    @WithMockUser(username = "admin", authorities = {"GENERIC", "ADMIN"})
    public void getClaimByIdTest() throws Exception {
        String rawToken = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_CLAIMS + "/1")
                        .header(Auth.AUTH_TOKEN_NAME, rawToken)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.approver.username", is("billk")))
                .andExpect(jsonPath("$.data.user.username", is("ninaa")))
                .andExpect(jsonPath("$.data.claimedAt", is("2016-06-10 09:00:00.000+0000")))
                .andExpect(jsonPath("$.data.approvedAt", is("2016-06-10 10:00:00.000+0000")))
                .andExpect(jsonPath("$.data.grantedAt", is("2016-06-10 10:20:00.000+0000")));
    }

    @Test
    @Transactional
    public void getCreateClaimTest() throws Exception {
        String rawToken = getTokenInfo();

        List<CreatePermissionClaimDTO> createClaimList = new LinkedList<>();
        CreatePermissionClaimDTO createPermissionClaimDTO = new CreatePermissionClaimDTO();
        createPermissionClaimDTO.setPermissionId(2L);
        createClaimList.add(createPermissionClaimDTO);

        ResultActions response = mockMvc.perform(
                post(RestEndpoints.API_V1_CLAIMS)
                        .header(Auth.AUTH_TOKEN_NAME, rawToken)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(createClaimList))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists());

        TypeRef<List<PermissionClaimDTO>> permissionCLaimListType = new TypeRef<List<PermissionClaimDTO>>() {
        };

        List<PermissionClaimDTO> permissionClaimDTOList = JsonPath
                .parse(response.andReturn().getResponse().getContentAsString())
                .read("$.data", permissionCLaimListType);

        PermissionClaimDTO claim = permissionClaimDTOList.get(0);

        Assert.assertEquals(2L, claim.getPermission().getId().longValue());
        Assert.assertEquals("admin", claim.getUser().getUsername());

    }


    @Test
    @Transactional
    public void getUpdateClaimTest() throws Exception {
        final Date verifyStartDate = new Date();

        final Date verifyEndDate = new Date();
        verifyEndDate.setTime(verifyStartDate.getTime() + 100000L);

        String rawToken = getTokenInfo();

        List<CreatePermissionClaimDTO> createClaimList = new LinkedList<>();
        CreatePermissionClaimDTO createPermissionClaimDTO = new CreatePermissionClaimDTO();
        createPermissionClaimDTO.setPermissionId(2L);
        createClaimList.add(createPermissionClaimDTO);

        ResultActions response = mockMvc.perform(
                post(RestEndpoints.API_V1_CLAIMS)
                        .header(Auth.AUTH_TOKEN_NAME, rawToken)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(createClaimList))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists());

        TypeRef<List<PermissionClaimDTO>> permissionClaimListType = new TypeRef<List<PermissionClaimDTO>>() {
        };

        List<PermissionClaimDTO> permissionClaimDTOList = JsonPath
                .parse(response.andReturn().getResponse().getContentAsString())
                .read("$.data", permissionClaimListType);

        PermissionClaimDTO claim = permissionClaimDTOList.get(0);

        Assert.assertNotNull(claim);


        claim.setEndAt(verifyEndDate);
        claim.setStartAt(verifyStartDate);

        Long claimId = claim.getId();
        response = mockMvc.perform(
                put(RestEndpoints.API_V1_CLAIMS)
                        .header(Auth.AUTH_TOKEN_NAME, rawToken)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(claim))
        )
                .andExpect(status().isAccepted())
        ;

        response = mockMvc.perform(
                get(RestEndpoints.API_V1_CLAIMS + "/" + claimId)
                        .header(Auth.AUTH_TOKEN_NAME, rawToken)
                        .accept(contentType)
        )
                .andExpect(status().isOk())
        ;

        PermissionClaimDTO updatedClaimFromRest = JsonPath
                .parse(response.andReturn().getResponse().getContentAsString())
                .read("$.data", PermissionClaimDTO.class);

        Assert.assertEquals(verifyStartDate, updatedClaimFromRest.getStartAt());
        Assert.assertEquals(verifyEndDate, updatedClaimFromRest.getEndAt());

    }


}
