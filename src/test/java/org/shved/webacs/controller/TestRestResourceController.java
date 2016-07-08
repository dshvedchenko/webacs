package org.shved.webacs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shved.webacs.constants.RestEndpoints;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.dto.ResourceDTO;
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
public class TestRestResourceController extends AbstractAppTest {


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Transactional
    @Test
    public void createResourceTest() throws Exception {

        String tokenStr = getTokenInfo();

        ResourceDTO rdto = new ResourceDTO();
        ResTypeDTO rtdto = getResTypeDTOById(1);
        rdto.setResType(rtdto);
        rdto.setName("future");
        rdto.setDetail("my future");


        ResultActions response = mockMvc.perform(
                post(RestEndpoints.API_V1_RESOURCES)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rdto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.resType.name", is("Calendar")))
                .andExpect(jsonPath("$.data.name", is("future")))
                .andExpect(jsonPath("$.data.detail", is("my future")));

        Integer newResourceId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");
        Assert.assertNotNull(newResourceId);
    }

    @Transactional
    @Test
    public void getResurceByIdTest() throws Exception {
        final long EXIST_RESOURCE_ID = 1L;

        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_RESOURCES + "/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("xDep Calendar")))
                .andExpect(jsonPath("$.data.resType.name", is("Calendar")))
                .andExpect(jsonPath("$.data.detail", is("xDep shared calendar")));

    }

    @Transactional
    @Test
    public void getResurceEditdTest() throws Exception {
        final long EXIST_RESOURCE_ID = 1L;

        final String EXISTS_RESOURCE_KIND = "Calendar";
        final String EXISTS_RESOURCE_DETAIL = "xDep shared calendar";

        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_RESOURCES + "/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("xDep Calendar")))
                .andExpect(jsonPath("$.data.resType.name", is(EXISTS_RESOURCE_KIND)))
                .andExpect(jsonPath("$.data.detail", is(EXISTS_RESOURCE_DETAIL)));

        Map resourceClientBag = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");

        resourceClientBag.replace("detail", "detail detail");
        resourceClientBag.replace("ownerPermissionId", 3);

        ResultActions updateResponce = mockMvc.perform(
                put(RestEndpoints.API_V1_RESOURCES + "/" + EXIST_RESOURCE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(resourceClientBag))
        ).andExpect(status().isAccepted());
    }

    @Transactional
    @Test
    public void getResurcesByType() throws Exception {
        final Integer EXIST_RESOURCE_TYPE_ID = 1;

        String tokenStr = getTokenInfo();

        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_RESOURCES + "/type/" + EXIST_RESOURCE_TYPE_ID)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("xDep Calendar")))
                .andExpect(jsonPath("$.data[0].resType.name", is("Calendar")))
                .andExpect(jsonPath("$.data[0].detail", is("xDep shared calendar")));

    }

    @Transactional
    @Test
    public void deleteResourceTest() throws Exception {

        String tokenStr = getTokenInfo();

        ResourceDTO rdto = new ResourceDTO();
        ResTypeDTO rtdto = getResTypeDTOById(1);
        rdto.setResType(rtdto);
        rdto.setName("future");
        rdto.setDetail("my future");


        ResultActions response = mockMvc.perform(
                post(RestEndpoints.API_V1_RESOURCES)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rdto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.resType.name", is("Calendar")))
                .andExpect(jsonPath("$.data.name", is("future")))
                .andExpect(jsonPath("$.data.detail", is("my future")));

        Integer newResourceId = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data.id");
        Assert.assertNotNull(newResourceId);

        response = mockMvc.perform(
                delete(RestEndpoints.API_V1_RESOURCES + "/" + newResourceId)
                        .header("X-AUTHID", tokenStr)
                        .accept(contentType)
                        .contentType(contentType)
                        .content(new ObjectMapper().writeValueAsString(rdto))
        )
                .andExpect(status().isAccepted());

    }


    private ResTypeDTO getResTypeDTOById(Integer resTypeId) throws Exception {
        ResultActions response = mockMvc.perform(
                get(RestEndpoints.API_V1_RESTYPES + "/" + resTypeId)
                        .header("X-AUTHID", getTokenInfo())
                        .accept(contentType)
                        .contentType(contentType)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id", is(resTypeId)));
        Map respData = JsonPath.read(response.andReturn().getResponse().getContentAsString(), "$.data");
        ResTypeDTO resTypeDTO = new ResTypeDTO();
        resTypeDTO.setId((Integer) respData.get("id"));
        resTypeDTO.setName((String) respData.get("name"));
        return resTypeDTO;
    }

}